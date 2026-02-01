package com.natalcard.natalcard.calc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for zodiac sign utilities
 */
class SignUtilTest {

    @Test
    void testGetSign() {
        assertEquals("ARIES", SignUtil.getSign(0.0));
        assertEquals("ARIES", SignUtil.getSign(15.0));
        assertEquals("TAURUS", SignUtil.getSign(30.0));
        assertEquals("TAURUS", SignUtil.getSign(45.0));
        assertEquals("GEMINI", SignUtil.getSign(60.0));
        assertEquals("CANCER", SignUtil.getSign(90.0));
        assertEquals("LEO", SignUtil.getSign(120.0));
        assertEquals("VIRGO", SignUtil.getSign(150.0));
        assertEquals("LIBRA", SignUtil.getSign(180.0));
        assertEquals("SCORPIO", SignUtil.getSign(210.0));
        assertEquals("SAGITTARIUS", SignUtil.getSign(240.0));
        assertEquals("CAPRICORN", SignUtil.getSign(270.0));
        assertEquals("AQUARIUS", SignUtil.getSign(300.0));
        assertEquals("PISCES", SignUtil.getSign(330.0));
        assertEquals("ARIES", SignUtil.getSign(360.0));
    }

    @Test
    void testGetSignDegree() {
        assertEquals(0.0, SignUtil.getSignDegree(0.0), 0.0001);
        assertEquals(15.5, SignUtil.getSignDegree(15.5), 0.0001);
        assertEquals(29.99, SignUtil.getSignDegree(29.99), 0.0001);
        assertEquals(0.0, SignUtil.getSignDegree(30.0), 0.0001);
        assertEquals(15.0, SignUtil.getSignDegree(45.0), 0.0001);
        assertEquals(0.0, SignUtil.getSignDegree(360.0), 0.0001);
    }

    @Test
    void testGetSignNumber() {
        assertEquals(0, SignUtil.getSignNumber(0.0));   // ARIES
        assertEquals(0, SignUtil.getSignNumber(29.9));  // ARIES
        assertEquals(1, SignUtil.getSignNumber(30.0));  // TAURUS
        assertEquals(2, SignUtil.getSignNumber(60.0));  // GEMINI
        assertEquals(11, SignUtil.getSignNumber(330.0)); // PISCES
        assertEquals(0, SignUtil.getSignNumber(360.0)); // ARIES (wraps around)
    }
}
