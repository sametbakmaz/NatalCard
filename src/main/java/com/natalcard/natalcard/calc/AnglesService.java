package com.natalcard.natalcard.calc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Calculate Ascendant and Midheaven (MC)
 */
@Service
@RequiredArgsConstructor
public class AnglesService {

    private final SiderealTimeService siderealTimeService;
    private final ObliquityService obliquityService;

    /**
     * Calculate chart angles (ASC and MC)
     * RAMC = LST
     * MC = atan2(sin(RAMC)*cos(ε), cos(RAMC))
     * ASC = atan2(sin(RAMC)*cos(ε) − tan(lat)*sin(ε), cos(RAMC))
     *
     * @return [ascendant, midheaven] in degrees
     */
    public double[] calculateAngles(double julianCenturiesT, double latitude, double longitude) {
        // Calculate GMST and LST
        double gmst = siderealTimeService.calculateGMST(julianCenturiesT);
        double lst = siderealTimeService.calculateLST(gmst, longitude);

        // Calculate obliquity
        double obliquity = obliquityService.calculateMeanObliquity(julianCenturiesT);

        // Convert to radians for trigonometry
        double lstRad = AstroMath.toRadians(lst);
        double obliquityRad = AstroMath.toRadians(obliquity);
        double latitudeRad = AstroMath.toRadians(latitude);

        // Calculate MC
        // mc = atan2(sin(LST)*cos(ε), cos(LST))
        double mcY = Math.sin(lstRad) * Math.cos(obliquityRad);
        double mcX = Math.cos(lstRad);
        double mc = AstroMath.atan2Degrees(mcY, mcX);

        // Calculate ASC
        // asc = atan2(sin(LST)*cos(ε) − tan(lat)*sin(ε), cos(LST))
        double ascY = Math.sin(lstRad) * Math.cos(obliquityRad)
                    - Math.tan(latitudeRad) * Math.sin(obliquityRad);
        double ascX = Math.cos(lstRad);
        double asc = AstroMath.atan2Degrees(ascY, ascX);

        return new double[]{asc, mc};
    }
}
