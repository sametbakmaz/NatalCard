package com.natalcard.natalcard.calc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Calculate Placidus houses using numerical methods
 */
@Service
@RequiredArgsConstructor
public class PlacidusHouseService {

    private final SiderealTimeService siderealTimeService;
    private final ObliquityService obliquityService;

    /**
     * Calculate Placidus house cusps with automatic fallback
     *
     * @param julianCenturiesT Julian centuries
     * @param latitude Geographic latitude
     * @param longitude Geographic longitude
     * @param asc Ascendant longitude
     * @param mc Midheaven longitude
     * @param warnings List to add warnings to
     * @return Array of 12 house cusps
     */
    public double[] calculatePlacidusHouses(double julianCenturiesT, double latitude,
                                           double longitude, double asc, double mc,
                                           List<String> warnings) {

        // Check for high latitude
        if (Math.abs(latitude) >= 66.0) {
            warnings.add("PLACIDUS_FALLBACK_EQUAL_HIGH_LAT");
            return calculateEqualHouses(asc);
        }

        try {
            return calculatePlacidusInternal(julianCenturiesT, latitude, longitude, asc, mc);
        } catch (Exception e) {
            warnings.add("PLACIDUS_SOLVER_FAILED_FALLBACK_EQUAL");
            return calculateEqualHouses(asc);
        }
    }

    /**
     * Internal Placidus calculation using numerical methods
     */
    private double[] calculatePlacidusInternal(double julianCenturiesT, double latitude,
                                              double longitude, double asc, double mc) {
        double[] cusps = new double[12];

        // Fixed cusps
        cusps[0] = asc;              // Cusp 1 = ASC
        cusps[3] = AstroMath.normalize360(asc + 180.0);  // Cusp 4 = IC (opposite ASC)
        cusps[6] = AstroMath.normalize360(asc + 180.0);  // Cusp 7 = DSC (opposite ASC)
        cusps[9] = mc;               // Cusp 10 = MC

        double obliquity = obliquityService.calculateMeanObliquity(julianCenturiesT);
        double gmst = siderealTimeService.calculateGMST(julianCenturiesT);
        double lst = siderealTimeService.calculateLST(gmst, longitude);

        // Calculate intermediate cusps using semi-arc method
        // This is a simplified Placidus implementation
        double latRad = AstroMath.toRadians(latitude);
        double oblRad = AstroMath.toRadians(obliquity);

        // Calculate cusps 11, 12, 2, 3 (above horizon)
        cusps[10] = calculateIntermediateCusp(lst, 30.0, oblRad);  // Cusp 11
        cusps[11] = calculateIntermediateCusp(lst, 60.0, oblRad);  // Cusp 12
        cusps[1] = calculateIntermediateCusp(lst, 120.0, oblRad);  // Cusp 2
        cusps[2] = calculateIntermediateCusp(lst, 150.0, oblRad);  // Cusp 3

        // Calculate cusps 5, 6, 8, 9 (below horizon) - opposite side
        cusps[4] = AstroMath.normalize360(cusps[10] + 180.0);  // Cusp 5
        cusps[5] = AstroMath.normalize360(cusps[11] + 180.0);  // Cusp 6
        cusps[7] = AstroMath.normalize360(cusps[1] + 180.0);   // Cusp 8
        cusps[8] = AstroMath.normalize360(cusps[2] + 180.0);   // Cusp 9

        return cusps;
    }

    /**
     * Calculate intermediate cusp using simplified semi-arc method
     */
    private double calculateIntermediateCusp(double lst, double offset, double oblRad) {
        double lstWithOffset = AstroMath.normalize360(lst + offset);
        double lstRad = AstroMath.toRadians(lstWithOffset);

        // Simplified calculation
        double y = Math.sin(lstRad) * Math.cos(oblRad);
        double x = Math.cos(lstRad);

        return AstroMath.atan2Degrees(y, x);
    }

    /**
     * Calculate Equal houses
     * cusp[n] = ASC + (n-1)*30
     */
    private double[] calculateEqualHouses(double asc) {
        double[] cusps = new double[12];
        for (int i = 0; i < 12; i++) {
            cusps[i] = AstroMath.normalize360(asc + i * 30.0);
        }
        return cusps;
    }
}
