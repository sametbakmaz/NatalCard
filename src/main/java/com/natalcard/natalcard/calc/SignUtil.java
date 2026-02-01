package com.natalcard.natalcard.calc;

import org.springframework.stereotype.Component;

/**
 * Zodiac sign utility
 */
@Component
public class SignUtil {

    private static final String[] SIGNS = {
        "ARIES", "TAURUS", "GEMINI", "CANCER", "LEO", "VIRGO",
        "LIBRA", "SCORPIO", "SAGITTARIUS", "CAPRICORN", "AQUARIUS", "PISCES"
    };

    /**
     * Get zodiac sign from ecliptic longitude
     * @param longitude Ecliptic longitude in degrees (0-360)
     * @return Sign name (ARIES, TAURUS, etc.)
     */
    public static String getSign(double longitude) {
        int index = (int) Math.floor(AstroMath.normalize360(longitude) / 30.0);
        return SIGNS[index % 12];
    }

    /**
     * Get degree within sign (0-30)
     * @param longitude Ecliptic longitude in degrees
     * @return Degree within the sign
     */
    public static double getSignDegree(double longitude) {
        return AstroMath.normalize360(longitude) % 30.0;
    }

    /**
     * Get sign number (0-11, where 0=ARIES)
     */
    public static int getSignNumber(double longitude) {
        return (int) Math.floor(AstroMath.normalize360(longitude) / 30.0) % 12;
    }
}
