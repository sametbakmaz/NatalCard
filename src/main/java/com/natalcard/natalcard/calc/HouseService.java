package com.natalcard.natalcard.calc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Main house calculation service with system selection
 */
@Service
@RequiredArgsConstructor
public class HouseService {

    private final PlacidusHouseService placidusHouseService;

    /**
     * Calculate house cusps based on requested system
     *
     * @param houseSystem "PLACIDUS", "EQUAL", or "WHOLE_SIGN"
     * @param julianCenturiesT Julian centuries from J2000
     * @param latitude Geographic latitude
     * @param longitude Geographic longitude
     * @param asc Ascendant longitude
     * @param mc Midheaven longitude
     * @param warnings List to add warnings to
     * @return Array of 12 house cusps (indices 0-11 for houses 1-12)
     */
    public double[] calculateHouses(String houseSystem, double julianCenturiesT,
                                   double latitude, double longitude,
                                   double asc, double mc, List<String> warnings) {

        if ("WHOLE_SIGN".equalsIgnoreCase(houseSystem)) {
            return calculateWholeSignHouses(asc);
        }

        if ("EQUAL".equalsIgnoreCase(houseSystem)) {
            return calculateEqualHouses(asc);
        }

        // Default to Placidus (with automatic fallback)
        return placidusHouseService.calculatePlacidusHouses(
            julianCenturiesT, latitude, longitude, asc, mc, warnings
        );
    }

    /**
     * Calculate Whole Sign houses
     *
     * CRITICAL: Whole Sign differs from Equal!
     * - cusp[1] = sign start of ASC (NOT ASC itself)
     * - cusp[n] = cusp[1] + (n-1)*30°
     *
     * Example: ASC = 172.56° (Virgo 22°)
     *   → cusp[1] = 150° (0° Virgo)
     *   → cusp[2] = 180° (0° Libra)
     *   → cusp[3] = 210° (0° Scorpio)
     *
     * @param asc Ascendant longitude
     * @return Array of 12 cusps, each at sign boundary (0° of each sign)
     */
    private double[] calculateWholeSignHouses(double asc) {
        double[] cusps = new double[12];

        // Find the sign start of ASC (burç başlangıcı)
        // signIndex = floor(asc / 30)
        // signStart = signIndex * 30
        double ascNormalized = AstroMath.normalize360(asc);
        int signIndex = (int) Math.floor(ascNormalized / 30.0);
        double cusp1 = signIndex * 30.0; // Sign boundary (0° of sign)

        // Each house = one zodiac sign (30°)
        for (int i = 0; i < 12; i++) {
            cusps[i] = AstroMath.normalize360(cusp1 + i * 30.0);
        }

        return cusps;
    }

    /**
     * Calculate Equal houses
     * cusp[n] = ASC + (n-1)*30°
     */
    private double[] calculateEqualHouses(double asc) {
        double[] cusps = new double[12];
        for (int i = 0; i < 12; i++) {
            cusps[i] = AstroMath.normalize360(asc + i * 30.0);
        }
        return cusps;
    }
}
