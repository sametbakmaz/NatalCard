package com.natalcard.natalcard.calc;

import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Julian Date calculations for astronomy
 */
@Component
public class JulianDate {

    private static final double UNIX_EPOCH_JD = 2440587.5;
    private static final double SECONDS_PER_DAY = 86400.0;
    public static final double J2000 = 2451545.0;

    /**
     * Convert Instant to Julian Date
     * JD = 2440587.5 + (epochSeconds + nanos/1e9) / 86400
     */
    public static double fromInstant(Instant instant) {
        long epochSeconds = instant.getEpochSecond();
        int nanos = instant.getNano();
        double totalSeconds = epochSeconds + (nanos / 1_000_000_000.0);
        return UNIX_EPOCH_JD + (totalSeconds / SECONDS_PER_DAY);
    }

    /**
     * Calculate Julian centuries from J2000.0
     * T = (JD - 2451545.0) / 36525
     */
    public static double toJulianCenturies(double jd) {
        return (jd - J2000) / 36525.0;
    }

    /**
     * Convert Instant directly to Julian centuries
     */
    public static double instantToJulianCenturies(Instant instant) {
        return toJulianCenturies(fromInstant(instant));
    }
}
