package com.natalcard.natalcard.service;

import com.natalcard.natalcard.api.dto.UtilityDTOs.*;
import com.natalcard.natalcard.i18n.AstroTranslations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for utility endpoints (pickers, selectors)
 * Provides static data for UI components
 */
@Service
@RequiredArgsConstructor
public class UtilityService {

    private final AstroTranslations translations;

    /**
     * Get all available timezones
     * Grouped by region and sorted by offset
     */
    public TimezoneListResponse getAllTimezones(String language) {
        List<TimezoneDTO> timezones = ZoneId.getAvailableZoneIds().stream()
            .filter(this::isValidTimezone)
            .map(id -> createTimezoneDTO(id, language))
            .sorted(Comparator.comparing(TimezoneDTO::getRegion)
                             .thenComparing(TimezoneDTO::getDisplayName))
            .collect(Collectors.toList());

        return TimezoneListResponse.builder()
            .timezones(timezones)
            .totalCount(timezones.size())
            .language(language)
            .build();
    }

    /**
     * Get popular timezones (for quick selection)
     */
    public TimezoneListResponse getPopularTimezones(String language) {
        List<String> popularIds = Arrays.asList(
            "Europe/Istanbul",
            "Europe/London",
            "Europe/Paris",
            "Europe/Berlin",
            "America/New_York",
            "America/Los_Angeles",
            "America/Chicago",
            "Asia/Tokyo",
            "Asia/Shanghai",
            "Asia/Dubai",
            "Australia/Sydney",
            "Pacific/Auckland"
        );

        List<TimezoneDTO> timezones = popularIds.stream()
            .map(id -> createTimezoneDTO(id, language))
            .collect(Collectors.toList());

        return TimezoneListResponse.builder()
            .timezones(timezones)
            .totalCount(timezones.size())
            .language(language)
            .build();
    }

    /**
     * Get all picker options (zodiac, houses, aspects)
     */
    public PickerOptionsResponse getAllPickerOptions(String language) {
        return PickerOptionsResponse.builder()
            .zodiacSystems(getZodiacSystems(language))
            .houseSystems(getHouseSystems(language))
            .aspectTypes(getAspectTypes(language))
            .language(language)
            .build();
    }

    /**
     * Get zodiac systems
     */
    public List<ZodiacSystemDTO> getZodiacSystems(String language) {
        boolean isTurkish = "tr".equalsIgnoreCase(language);

        return Arrays.asList(
            ZodiacSystemDTO.builder()
                .code("TROPICAL")
                .name("Tropical")
                .nameLocalized(isTurkish ? "Tropikal" : "Tropical")
                .description("Based on seasons and equinoxes (most common in Western astrology)")
                .descriptionLocalized(isTurkish ?
                    "Mevsim ve ekinokslara dayalı (Batı astrolojisinde en yaygın)" :
                    "Based on seasons and equinoxes (most common in Western astrology)")
                .supported(true)
                .build(),
            ZodiacSystemDTO.builder()
                .code("SIDEREAL")
                .name("Sidereal")
                .nameLocalized(isTurkish ? "Sidereal" : "Sidereal")
                .description("Based on fixed stars (used in Vedic astrology)")
                .descriptionLocalized(isTurkish ?
                    "Sabit yıldızlara dayalı (Vedik astrolojide kullanılır)" :
                    "Based on fixed stars (used in Vedic astrology)")
                .supported(false) // Not supported yet
                .build()
        );
    }

    /**
     * Get house systems
     */
    public List<HouseSystemDTO> getHouseSystems(String language) {
        boolean isTurkish = "tr".equalsIgnoreCase(language);

        return Arrays.asList(
            HouseSystemDTO.builder()
                .code("PLACIDUS")
                .name("Placidus")
                .nameLocalized(translations.translateHouseSystem("PLACIDUS", language))
                .description("Most popular house system, quadrant-based")
                .descriptionLocalized(isTurkish ?
                    "En popüler ev sistemi, kadran tabanlı" :
                    "Most popular house system, quadrant-based")
                .recommended(true)
                .bestFor("Most latitudes (automatic fallback at poles)")
                .bestForLocalized(isTurkish ?
                    "Çoğu enlem (kutuplarda otomatik yedek)" :
                    "Most latitudes (automatic fallback at poles)")
                .build(),

            HouseSystemDTO.builder()
                .code("WHOLE_SIGN")
                .name("Whole Sign")
                .nameLocalized(translations.translateHouseSystem("WHOLE_SIGN", language))
                .description("Traditional Hellenistic system, each house = one sign")
                .descriptionLocalized(isTurkish ?
                    "Geleneksel Helenistik sistem, her ev = bir burç" :
                    "Traditional Hellenistic system, each house = one sign")
                .recommended(false)
                .bestFor("All latitudes, historical accuracy")
                .bestForLocalized(isTurkish ?
                    "Tüm enlemler, tarihsel doğruluk" :
                    "All latitudes, historical accuracy")
                .build(),

            HouseSystemDTO.builder()
                .code("EQUAL")
                .name("Equal")
                .nameLocalized(translations.translateHouseSystem("EQUAL", language))
                .description("Simplest system, each house exactly 30 degrees")
                .descriptionLocalized(isTurkish ?
                    "En basit sistem, her ev tam 30 derece" :
                    "Simplest system, each house exactly 30 degrees")
                .recommended(false)
                .bestFor("All latitudes, simplicity")
                .bestForLocalized(isTurkish ?
                    "Tüm enlemler, basitlik" :
                    "All latitudes, simplicity")
                .build(),

            HouseSystemDTO.builder()
                .code("KOCH")
                .name("Koch")
                .nameLocalized(translations.translateHouseSystem("KOCH", language))
                .description("Birthplace system, similar to Placidus")
                .descriptionLocalized(isTurkish ?
                    "Doğum yeri sistemi, Placidus'a benzer" :
                    "Birthplace system, similar to Placidus")
                .recommended(false)
                .bestFor("Most latitudes")
                .bestForLocalized(isTurkish ?
                    "Çoğu enlem" :
                    "Most latitudes")
                .build()
        );
    }

    /**
     * Get aspect types
     */
    public List<AspectTypeDTO> getAspectTypes(String language) {
        return Arrays.asList(
            AspectTypeDTO.builder()
                .code("CONJUNCTION")
                .name("Conjunction")
                .nameLocalized(translations.translateAspect("CONJUNCTION", language))
                .angle(0.0)
                .symbol("☌")
                .meaning("Unity, blending")
                .meaningLocalized("tr".equalsIgnoreCase(language) ?
                    "Birlik, kaynaşma" : "Unity, blending")
                .color("#9C27B0") // Purple
                .build(),

            AspectTypeDTO.builder()
                .code("SEXTILE")
                .name("Sextile")
                .nameLocalized(translations.translateAspect("SEXTILE", language))
                .angle(60.0)
                .symbol("⚹")
                .meaning("Opportunity, harmony")
                .meaningLocalized("tr".equalsIgnoreCase(language) ?
                    "Fırsat, uyum" : "Opportunity, harmony")
                .color("#4CAF50") // Green
                .build(),

            AspectTypeDTO.builder()
                .code("SQUARE")
                .name("Square")
                .nameLocalized(translations.translateAspect("SQUARE", language))
                .angle(90.0)
                .symbol("□")
                .meaning("Tension, challenge")
                .meaningLocalized("tr".equalsIgnoreCase(language) ?
                    "Gerilim, zorluk" : "Tension, challenge")
                .color("#F44336") // Red
                .build(),

            AspectTypeDTO.builder()
                .code("TRINE")
                .name("Trine")
                .nameLocalized(translations.translateAspect("TRINE", language))
                .angle(120.0)
                .symbol("△")
                .meaning("Flow, ease, talent")
                .meaningLocalized("tr".equalsIgnoreCase(language) ?
                    "Akış, kolaylık, yetenek" : "Flow, ease, talent")
                .color("#2196F3") // Blue
                .build(),

            AspectTypeDTO.builder()
                .code("OPPOSITION")
                .name("Opposition")
                .nameLocalized(translations.translateAspect("OPPOSITION", language))
                .angle(180.0)
                .symbol("☍")
                .meaning("Polarity, awareness")
                .meaningLocalized("tr".equalsIgnoreCase(language) ?
                    "Karşıtlık, farkındalık" : "Polarity, awareness")
                .color("#FF9800") // Orange
                .build()
        );
    }

    // Helper methods

    private boolean isValidTimezone(String zoneId) {
        // Filter out system timezones like "Etc/GMT+X"
        return !zoneId.startsWith("Etc/") &&
               !zoneId.startsWith("SystemV/") &&
               zoneId.contains("/");
    }

    private TimezoneDTO createTimezoneDTO(String zoneId, String language) {
        ZoneId zone = ZoneId.of(zoneId);
        String[] parts = zoneId.split("/");
        String region = parts[0];
        String location = parts.length > 1 ? parts[1].replace("_", " ") : "";

        // Get offset (simplified - not accounting for DST)
        String offset = zone.getRules().getOffset(java.time.Instant.now()).toString();

        return TimezoneDTO.builder()
            .id(zoneId)
            .displayName(location + " (" + offset + ")")
            .displayNameLocalized(translateLocation(location, language) + " (" + offset + ")")
            .offset(offset)
            .region(region)
            .country(location)
            .countryLocalized(translateLocation(location, language))
            .build();
    }

    private String translateLocation(String location, String language) {
        // Basic translations for common locations
        if (!"tr".equalsIgnoreCase(language)) {
            return location;
        }

        Map<String, String> translations = new HashMap<>();
        translations.put("Istanbul", "İstanbul");
        translations.put("Ankara", "Ankara");
        translations.put("London", "Londra");
        translations.put("Paris", "Paris");
        translations.put("New York", "New York");
        translations.put("Tokyo", "Tokyo");
        translations.put("Dubai", "Dubai");
        translations.put("Sydney", "Sidney");

        return translations.getOrDefault(location, location);
    }
}
