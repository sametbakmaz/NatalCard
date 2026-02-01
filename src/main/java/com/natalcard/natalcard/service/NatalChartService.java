package com.natalcard.natalcard.service;

import com.natalcard.natalcard.api.dto.NatalChartRequestDTO;
import com.natalcard.natalcard.api.dto.NatalChartResponseDTO;
import com.natalcard.natalcard.calc.*;
import com.natalcard.natalcard.i18n.AstroTranslations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Main service for natal chart calculation
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NatalChartService {

    private final PlanetPositionService planetPositionService;
    private final AnglesService anglesService;
    private final HouseService houseService;
    private final AspectService aspectService;
    private final AstroTranslations translations;

    /**
     * Calculate complete natal chart
     */
    public NatalChartResponseDTO calculateNatalChart(NatalChartRequestDTO request) {
        List<String> warnings = new ArrayList<>();

        // Validate zodiac (only TROPICAL supported)
        if (!"TROPICAL".equalsIgnoreCase(request.getZodiac())) {
            throw new IllegalArgumentException("Only TROPICAL zodiac is supported");
        }

        // Convert local datetime to UTC Instant (trim spaces for flexible parsing)
        String birthDateTime = request.getBirthDateTimeLocal().replaceAll("\\s+", "");
        LocalDateTime localDateTime = LocalDateTime.parse(birthDateTime);
        ZoneId zoneId = ZoneId.of(request.getTimeZoneId());
        Instant instant = localDateTime.atZone(zoneId).toInstant();

        // Calculate Julian centuries
        double julianCenturiesT = JulianDate.instantToJulianCenturies(instant);

        // Calculate planet positions
        Map<String, Double> planetPositions = planetPositionService.calculatePlanetPositions(instant);

        // Calculate angles (ASC and MC)
        double[] angles = anglesService.calculateAngles(
            julianCenturiesT,
            request.getLatitude(),
            request.getLongitude()
        );
        double asc = angles[0];
        double mc = angles[1];

        // Calculate houses
        String requestedHouseSystem = request.getHouseSystem().toUpperCase();
        double[] houseCusps = houseService.calculateHouses(
            requestedHouseSystem,
            julianCenturiesT,
            request.getLatitude(),
            request.getLongitude(),
            asc,
            mc,
            warnings
        );

        // Determine effective house system
        String effectiveHouseSystem = determineEffectiveHouseSystem(requestedHouseSystem, warnings);

        // Get language (default: "en")
        String language = request.getLanguage() != null ? request.getLanguage() : "en";

        // Translate warnings
        List<String> warningsLocalized = new ArrayList<>();
        for (String warning : warnings) {
            warningsLocalized.add(translations.translateWarning(warning, language));
        }

        // Build response
        return NatalChartResponseDTO.builder()
            .meta(NatalChartResponseDTO.MetaDTO.builder()
                .requestedHouseSystem(requestedHouseSystem)
                .effectiveHouseSystem(effectiveHouseSystem)
                .warnings(warnings)
                // Localized
                .requestedHouseSystemLocalized(translations.translateHouseSystem(requestedHouseSystem, language))
                .effectiveHouseSystemLocalized(translations.translateHouseSystem(effectiveHouseSystem, language))
                .warningsLocalized(warningsLocalized)
                .build())
            .angles(buildAnglesDTO(asc, mc, language))
            .houses(buildHousesDTO(houseCusps, language))
            .points(buildPointsDTO(planetPositions, houseCusps, asc, effectiveHouseSystem, language))
            .aspects(request.getIncludeAspects() ? buildAspectsDTO(planetPositions, language) : new ArrayList<>())
            .build();
    }

    private String determineEffectiveHouseSystem(String requested, List<String> warnings) {
        for (String warning : warnings) {
            if (warning.contains("FALLBACK_EQUAL")) {
                return "EQUAL";
            }
        }
        return requested;
    }

    private NatalChartResponseDTO.AnglesDTO buildAnglesDTO(double asc, double mc, String language) {
        String ascSign = SignUtil.getSign(asc);
        String mcSign = SignUtil.getSign(mc);

        return NatalChartResponseDTO.AnglesDTO.builder()
            .ascendantLongitude(asc)
            .midHeavenLongitude(mc)
            .ascendantSign(ascSign)
            .midHeavenSign(mcSign)
            // Localized
            .ascendantSignLocalized(translations.translateSign(ascSign, language))
            .midHeavenSignLocalized(translations.translateSign(mcSign, language))
            .build();
    }

    private List<NatalChartResponseDTO.HouseDTO> buildHousesDTO(double[] cusps, String language) {
        List<NatalChartResponseDTO.HouseDTO> houses = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            String sign = SignUtil.getSign(cusps[i]);
            houses.add(NatalChartResponseDTO.HouseDTO.builder()
                .number(i + 1)
                .cuspLongitude(cusps[i])
                .sign(sign)
                // Localized
                .signLocalized(translations.translateSign(sign, language))
                .build());
        }
        return houses;
    }

    private Map<String, NatalChartResponseDTO.PlanetPointDTO> buildPointsDTO(
            Map<String, Double> planetPositions, double[] houseCusps, double asc, String houseSystem, String language) {

        Map<String, NatalChartResponseDTO.PlanetPointDTO> points = new LinkedHashMap<>();

        for (Map.Entry<String, Double> entry : planetPositions.entrySet()) {
            String planetName = entry.getKey();
            Double longitude = entry.getValue();

            // Determine house based on system
            int house;
            if ("WHOLE_SIGN".equalsIgnoreCase(houseSystem)) {
                house = determineWholeSignHouse(longitude, asc);
            } else {
                house = HouseUtil.determineHouse(longitude, houseCusps);
            }

            String sign = SignUtil.getSign(longitude);

            points.put(planetName, NatalChartResponseDTO.PlanetPointDTO.builder()
                .name(planetName)
                .longitude(longitude)
                .sign(sign)
                .house(house)
                .signDegree(SignUtil.getSignDegree(longitude))
                // Localized
                .nameLocalized(translations.translatePlanet(planetName, language))
                .signLocalized(translations.translateSign(sign, language))
                .build());
        }

        return points;
    }

    /**
     * Determine house for WHOLE_SIGN system
     * Formula: house = ((signIndex(planet) - signIndex(ASC) + 12) % 12) + 1
     */
    private int determineWholeSignHouse(double planetLon, double ascLon) {
        int planetSign = SignUtil.getSignNumber(planetLon);
        int ascSign = SignUtil.getSignNumber(ascLon);
        return ((planetSign - ascSign + 12) % 12) + 1;
    }

    private List<NatalChartResponseDTO.AspectDTO> buildAspectsDTO(Map<String, Double> planetPositions, String language) {
        List<AspectService.Aspect> aspects = aspectService.calculateAspects(planetPositions);
        List<NatalChartResponseDTO.AspectDTO> result = new ArrayList<>();

        for (AspectService.Aspect aspect : aspects) {
            result.add(NatalChartResponseDTO.AspectDTO.builder()
                .planet1(aspect.getPlanet1())
                .planet2(aspect.getPlanet2())
                .aspectType(aspect.getType().getName())
                .angle(aspect.getAngle())
                .orb(aspect.getOrb())
                .isApplying(aspect.getApplying())
                // Localized
                .planet1Localized(translations.translatePlanet(aspect.getPlanet1(), language))
                .planet2Localized(translations.translatePlanet(aspect.getPlanet2(), language))
                .aspectTypeLocalized(translations.translateAspect(aspect.getType().getName(), language))
                .build());
        }

        return result;
    }
}
