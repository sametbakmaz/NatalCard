package com.natalcard.natalcard.calc;

import org.springframework.stereotype.Component;

/**
 * Calculate mean obliquity of the ecliptic
 */
@Component
public class ObliquityService {

    /**
     * Calculate mean obliquity ε (epsilon) in degrees
     * Formula: ε = (84381.448 − 46.8150*T − 0.00059*T² + 0.001813*T³) / 3600
     * where T = Julian centuries from J2000.0
     */
    public double calculateMeanObliquity(double julianCenturiesT) {
        double T2 = julianCenturiesT * julianCenturiesT;
        double T3 = T2 * julianCenturiesT;

        // Calculate in arcseconds
        double epsilonArcseconds = 84381.448
                                  - 46.8150 * julianCenturiesT
                                  - 0.00059 * T2
                                  + 0.001813 * T3;

        // Convert to degrees
        return epsilonArcseconds / 3600.0;
    }
}
