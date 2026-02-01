package com.natalcard.natalcard.calc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for astronomical math utilities
 */
class AstroMathTest {

    @Test
    void testNormalize360() {
        assertEquals(45.0, AstroMath.normalize360(45.0), 0.0001);
        assertEquals(45.0, AstroMath.normalize360(405.0), 0.0001);
        assertEquals(315.0, AstroMath.normalize360(-45.0), 0.0001);
        assertEquals(0.0, AstroMath.normalize360(360.0), 0.0001);
        assertEquals(0.0, AstroMath.normalize360(720.0), 0.0001);
    }

    @Test
    void testMinimalAngleDifference() {
        assertEquals(10.0, AstroMath.minimalAngleDifference(350.0, 0.0), 0.0001);
        assertEquals(-10.0, AstroMath.minimalAngleDifference(0.0, 350.0), 0.0001);
        assertEquals(90.0, AstroMath.minimalAngleDifference(0.0, 90.0), 0.0001);
        assertEquals(180.0, Math.abs(AstroMath.minimalAngleDifference(0.0, 180.0)), 0.0001);
    }

    @Test
    void testDegreesRadiansConversion() {
        double degrees = 90.0;
        double radians = AstroMath.toRadians(degrees);
        double backToDegrees = AstroMath.toDegrees(radians);
        assertEquals(degrees, backToDegrees, 0.0001);
    }
}
