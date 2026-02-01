package com.natalcard.natalcard.calc;

import org.springframework.stereotype.Component;

/**
 * House-related utilities
 */
@Component
public class HouseUtil {

    /**
     * Determine which house a planet is in based on house cusps
     * Uses forward zodiac arc between cusps
     *
     * @param planetLon Planet longitude (0-360)
     * @param cusps Array of 12 house cusps (1-12), starting at index 0
     * @return House number (1-12)
     */
    public static int determineHouse(double planetLon, double[] cusps) {
        planetLon = AstroMath.normalize360(planetLon);

        for (int house = 1; house <= 12; house++) {
            double cuspThis = AstroMath.normalize360(cusps[house - 1]);
            double cuspNext = AstroMath.normalize360(cusps[house % 12]); // wraps to cusp[0] after cusp[11]

            // Check if planet is in this house (between cuspThis and cuspNext)
            if (isInHouse(planetLon, cuspThis, cuspNext)) {
                return house;
            }
        }

        // Fallback (should not happen)
        return 1;
    }

    /**
     * Check if a longitude is within a house defined by two cusps
     * Handles 360-degree wrap-around correctly
     *
     * A planet is in a house if it's between the house cusp and the next house cusp,
     * moving forward through the zodiac (counterclockwise).
     */
    private static boolean isInHouse(double lon, double cuspStart, double cuspEnd) {
        // All values already normalized to 0-360

        if (cuspStart <= cuspEnd) {
            // Normal case: cusp1=34°, cusp2=175°
            // Planet is in house if: 34° <= planet < 175°
            return lon >= cuspStart && lon < cuspEnd;
        } else {
            // Wrap-around case: cusp1=355°, cusp2=23°
            // Planet is in house if: (planet >= 355°) OR (planet < 23°)
            return lon >= cuspStart || lon < cuspEnd;
        }
    }
}
