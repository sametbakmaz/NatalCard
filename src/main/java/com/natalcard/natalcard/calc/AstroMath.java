package com.natalcard.natalcard.calc;

import org.springframework.stereotype.Component;

/**
 * Utility class for astronomical mathematics
 */
@Component
public class AstroMath {

    /**
     * Normalize angle to 0-360 range
     */
    public static double normalize360(double angle) {
        double result = angle % 360.0;
        if (result < 0) {
            result += 360.0;
        }
        return result;
    }

    /**
     * Calculate minimal angle difference between two angles
     * Returns value between -180 and +180
     */
    public static double minimalAngleDifference(double angle1, double angle2) {
        double diff = angle2 - angle1;
        while (diff > 180.0) diff -= 360.0;
        while (diff < -180.0) diff += 360.0;
        return diff;
    }

    /**
     * Convert degrees to radians
     */
    public static double toRadians(double degrees) {
        return Math.toRadians(degrees);
    }

    /**
     * Convert radians to degrees
     */
    public static double toDegrees(double radians) {
        return Math.toDegrees(radians);
    }

    /**
     * Calculate arctangent with proper quadrant handling
     */
    public static double atan2Degrees(double y, double x) {
        return normalize360(toDegrees(Math.atan2(y, x)));
    }
}
