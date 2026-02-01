package com.natalcard.natalcard.calc;

import org.springframework.stereotype.Component;

/**
 * Calculate Greenwich Mean Sidereal Time (GMST) and Local Sidereal Time (LST)
 */
@Component
public class SiderealTimeService {

    /**
     * Calculate Greenwich Mean Sidereal Time in degrees
     * Formula:
     * GMST_seconds = 67310.54841 + (876600*3600 + 8640184.812866)*T + 0.093104*T² − 6.2e−6*T³
     * GMST_degrees = GMST_seconds / 240
     *
     * @param julianCenturiesT Julian centuries from J2000.0
     * @return GMST in degrees (0-360)
     */
    public double calculateGMST(double julianCenturiesT) {
        double T2 = julianCenturiesT * julianCenturiesT;
        double T3 = T2 * julianCenturiesT;

        // Calculate GMST in seconds
        double gmstSeconds = 67310.54841
                           + (876600.0 * 3600.0 + 8640184.812866) * julianCenturiesT
                           + 0.093104 * T2
                           - 6.2e-6 * T3;

        // Convert to degrees: 240 seconds = 1 degree
        double gmstDegrees = gmstSeconds / 240.0;

        // Normalize to 0-360
        return AstroMath.normalize360(gmstDegrees);
    }

    /**
     * Calculate Local Sidereal Time
     * LST = GMST + longitude
     *
     * @param gmstDegrees GMST in degrees
     * @param longitudeDegrees Geographic longitude in degrees (positive East)
     * @return LST in degrees (0-360)
     */
    public double calculateLST(double gmstDegrees, double longitudeDegrees) {
        return AstroMath.normalize360(gmstDegrees + longitudeDegrees);
    }
}
