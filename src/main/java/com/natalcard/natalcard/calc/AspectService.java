package com.natalcard.natalcard.calc;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Calculate aspects between planets
 */
@Service
public class AspectService {

    @Data
    @AllArgsConstructor
    public static class Aspect {
        private String planet1;
        private String planet2;
        private AspectType type;
        private double angle;
        private double orb;
        private Boolean applying; // Nullable: null when planet speeds are not calculated
    }

    public enum AspectType {
        CONJUNCTION(0, "CONJUNCTION"),
        SEXTILE(60, "SEXTILE"),
        SQUARE(90, "SQUARE"),
        TRINE(120, "TRINE"),
        OPPOSITION(180, "OPPOSITION");

        private final double angle;
        private final String name;

        AspectType(double angle, String name) {
            this.angle = angle;
            this.name = name;
        }

        public double getAngle() {
            return angle;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Calculate all major aspects between planets
     * Orbs:
     * - Sun/Moon involved: 8°
     * - Others: 6°
     *
     * @param planetPositions Map of planet name to longitude
     * @return List of aspects
     */
    public List<Aspect> calculateAspects(Map<String, Double> planetPositions) {
        List<Aspect> aspects = new ArrayList<>();

        List<String> planets = new ArrayList<>(planetPositions.keySet());

        // Check all planet pairs
        for (int i = 0; i < planets.size(); i++) {
            for (int j = i + 1; j < planets.size(); j++) {
                String planet1 = planets.get(i);
                String planet2 = planets.get(j);

                double lon1 = planetPositions.get(planet1);
                double lon2 = planetPositions.get(planet2);

                // Calculate angular difference
                double diff = Math.abs(AstroMath.minimalAngleDifference(lon1, lon2));

                // Determine orb based on luminaries
                double allowedOrb = isSunOrMoon(planet1) || isSunOrMoon(planet2) ? 8.0 : 6.0;

                // Check each aspect type
                for (AspectType aspectType : AspectType.values()) {
                    double aspectAngle = aspectType.getAngle();
                    double orb = Math.abs(diff - aspectAngle);

                    if (orb <= allowedOrb) {
                        aspects.add(new Aspect(
                            planet1,
                            planet2,
                            aspectType,
                            aspectAngle,
                            orb,
                            null // Planet speeds not calculated - applying/separating unknown
                        ));
                        break; // Only one aspect per pair
                    }
                }
            }
        }

        return aspects;
    }

    private boolean isSunOrMoon(String planet) {
        return "SUN".equals(planet) || "MOON".equals(planet);
    }
}
