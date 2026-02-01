package com.natalcard.natalcard.calc;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Calculate planet positions using simplified VSOP87 algorithms
 * This is a simplified implementation for tropical zodiac calculations
 */
@Service
public class PlanetPositionService {

    private static final String[] PLANET_NAMES = {
        "SUN", "MOON", "MERCURY", "VENUS", "MARS",
        "JUPITER", "SATURN", "URANUS", "NEPTUNE", "PLUTO"
    };

    /**
     * Calculate all planet positions at given instant
     *
     * @param instant Birth time in UTC
     * @return Map of planet name to ecliptic longitude (0-360°)
     */
    public Map<String, Double> calculatePlanetPositions(Instant instant) {
        Map<String, Double> positions = new LinkedHashMap<>();

        double jd = JulianDate.fromInstant(instant);
        double T = JulianDate.toJulianCenturies(jd);

        // Calculate positions using simplified formulas
        positions.put("SUN", calculateSunPosition(T));
        positions.put("MOON", calculateMoonPosition(T));
        positions.put("MERCURY", calculateMercuryPosition(T));
        positions.put("VENUS", calculateVenusPosition(T));
        positions.put("MARS", calculateMarsPosition(T));
        positions.put("JUPITER", calculateJupiterPosition(T));
        positions.put("SATURN", calculateSaturnPosition(T));
        positions.put("URANUS", calculateUranusPosition(T));
        positions.put("NEPTUNE", calculateNeptunePosition(T));
        positions.put("PLUTO", calculatePlutoPosition(T));

        return positions;
    }

    /**
     * Calculate Sun's ecliptic longitude (simplified)
     * Based on VSOP87 with major terms only
     */
    private double calculateSunPosition(double T) {
        // Sun's mean longitude
        double L0 = 280.46646 + 36000.76983 * T + 0.0003032 * T * T;

        // Mean anomaly
        double M = 357.52911 + 35999.05029 * T - 0.0001537 * T * T;
        double MRad = Math.toRadians(M);

        // Equation of center
        double C = (1.914602 - 0.004817 * T - 0.000014 * T * T) * Math.sin(MRad)
                 + (0.019993 - 0.000101 * T) * Math.sin(2 * MRad)
                 + 0.000289 * Math.sin(3 * MRad);

        // True longitude
        double sunLon = L0 + C;

        return AstroMath.normalize360(sunLon);
    }

    /**
     * Calculate Moon's ecliptic longitude (simplified)
     * Based on ELP2000 with major terms
     */
    private double calculateMoonPosition(double T) {
        // Moon's mean longitude
        double L = 218.3164477 + 481267.88123421 * T
                 - 0.0015786 * T * T
                 + T * T * T / 538841.0
                 - T * T * T * T / 65194000.0;

        // Mean elongation
        double D = 297.8501921 + 445267.1114034 * T
                 - 0.0018819 * T * T
                 + T * T * T / 545868.0
                 - T * T * T * T / 113065000.0;

        // Sun's mean anomaly
        double M = 357.5291092 + 35999.0502909 * T
                 - 0.0001536 * T * T
                 + T * T * T / 24490000.0;

        // Moon's mean anomaly
        double MPrime = 134.9633964 + 477198.8675055 * T
                      + 0.0087414 * T * T
                      + T * T * T / 69699.0
                      - T * T * T * T / 14712000.0;

        // Moon's argument of latitude
        double F = 93.2720950 + 483202.0175233 * T
                 - 0.0036539 * T * T
                 - T * T * T / 3526000.0
                 + T * T * T * T / 863310000.0;

        // Convert to radians
        double DRad = Math.toRadians(D);
        double MRad = Math.toRadians(M);
        double MPrimeRad = Math.toRadians(MPrime);
        double FRad = Math.toRadians(F);

        // Major periodic terms (simplified - top 10 terms)
        double correction = 0;
        correction += 6.288774 * Math.sin(MPrimeRad);
        correction += 1.274027 * Math.sin(2 * DRad - MPrimeRad);
        correction += 0.658314 * Math.sin(2 * DRad);
        correction += 0.213618 * Math.sin(2 * MPrimeRad);
        correction -= 0.185116 * Math.sin(MRad);
        correction -= 0.114332 * Math.sin(2 * FRad);
        correction += 0.058793 * Math.sin(2 * DRad - 2 * MPrimeRad);
        correction += 0.057066 * Math.sin(2 * DRad - MRad - MPrimeRad);
        correction += 0.053322 * Math.sin(2 * DRad + MPrimeRad);
        correction += 0.045758 * Math.sin(2 * DRad - MRad);

        double moonLon = L + correction;

        return AstroMath.normalize360(moonLon);
    }

    /**
     * Calculate Mercury's position (simplified VSOP87)
     */
    private double calculateMercuryPosition(double T) {
        double L = 252.250906 + 149472.6746358 * T;
        double a = 0.387098;
        return calculateInnerPlanet(L, a, T, 0.205635, 7.004986);
    }

    /**
     * Calculate Venus's position (simplified VSOP87)
     */
    private double calculateVenusPosition(double T) {
        double L = 181.979801 + 58517.8156760 * T;
        double a = 0.723330;
        return calculateInnerPlanet(L, a, T, 0.006772, 3.394662);
    }

    /**
     * Calculate Mars's position (simplified VSOP87)
     */
    private double calculateMarsPosition(double T) {
        double L = 355.433000 + 19140.2993039 * T;
        double a = 1.523688;
        return calculateOuterPlanet(L, a, T, 0.093405, 1.849726);
    }

    /**
     * Calculate Jupiter's position (simplified VSOP87)
     */
    private double calculateJupiterPosition(double T) {
        double L = 34.351519 + 3034.9056606 * T;
        double a = 5.202603;
        return calculateOuterPlanet(L, a, T, 0.048498, 1.303267);
    }

    /**
     * Calculate Saturn's position (simplified VSOP87)
     */
    private double calculateSaturnPosition(double T) {
        double L = 50.077444 + 1222.1138488 * T;
        double a = 9.554909;
        return calculateOuterPlanet(L, a, T, 0.055546, 2.488879);
    }

    /**
     * Calculate Uranus's position (simplified)
     */
    private double calculateUranusPosition(double T) {
        double L = 314.055005 + 428.4669983 * T;
        double a = 19.218446;
        return calculateOuterPlanet(L, a, T, 0.046381, 0.772556);
    }

    /**
     * Calculate Neptune's position (simplified)
     */
    private double calculateNeptunePosition(double T) {
        double L = 304.348665 + 218.4862002 * T;
        double a = 30.110387;
        return calculateOuterPlanet(L, a, T, 0.009456, 1.769953);
    }

    /**
     * Calculate Pluto's position (simplified)
     */
    private double calculatePlutoPosition(double T) {
        // Simplified formula for Pluto
        double L = 238.92881 + 145.18042 * T;
        return AstroMath.normalize360(L);
    }

    /**
     * Helper: Calculate inner planet position
     */
    private double calculateInnerPlanet(double L, double a, double T, double e, double i) {
        // This is a simplified calculation
        // In a production system, you would use full VSOP87 terms
        double correction = e * Math.sin(Math.toRadians(L)) * 57.2958; // Rough equation of center
        return AstroMath.normalize360(L + correction);
    }

    /**
     * Helper: Calculate outer planet position
     */
    private double calculateOuterPlanet(double L, double a, double T, double e, double i) {
        // This is a simplified calculation
        // In a production system, you would use full VSOP87 terms
        double correction = e * Math.sin(Math.toRadians(L)) * 57.2958; // Rough equation of center
        return AstroMath.normalize360(L + correction);
    }
}
