package com.natalcard.natalcard.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Utility API endpoints
 * Tests picker/selector endpoints for mobile apps
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UtilityControllerTest {

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void testGetPickerOptions_English() {
        String responseBody = webTestClient.get()
                .uri("/api/util/picker-options?language=en")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        System.out.println("\n=== RESPONSE BODY (Picker Options EN) ===");
        System.out.println(responseBody);
        System.out.println("==========================================\n");

        assertNotNull(responseBody);
        assertTrue(responseBody.contains("\"language\"") && responseBody.contains("\"en\""), "Should contain language:en");
        assertTrue(responseBody.contains("zodiacSystems"), "Should contain zodiacSystems");
        assertTrue(responseBody.contains("houseSystems"), "Should contain houseSystems");
        assertTrue(responseBody.contains("aspectTypes"), "Should contain aspectTypes");
        assertTrue(responseBody.contains("TROPICAL"), "Should contain TROPICAL");
        assertTrue(responseBody.contains("PLACIDUS"), "Should contain PLACIDUS");

        System.out.println("✅ Picker options (EN) - OK");
    }

    @Test
    void testGetPickerOptions_Turkish() {
        String responseBody = webTestClient.get()
                .uri("/api/util/picker-options?language=tr")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        System.out.println("\n=== RESPONSE BODY (Picker Options TR) ===");
        System.out.println(responseBody);
        System.out.println("==========================================\n");

        assertNotNull(responseBody);
        assertTrue(responseBody.contains("\"language\"") && responseBody.contains("\"tr\""), "Should contain language:tr");
        assertTrue(responseBody.contains("Tropikal"), "Should contain Tropikal");
        assertTrue(responseBody.contains("Tam Burç") || responseBody.contains("Tam Burc"), "Should contain Tam Burç");
        assertTrue(responseBody.contains("Kavuşum") || responseBody.contains("Kavusum"), "Should contain Kavuşum");

        System.out.println("✅ Picker options (TR) - OK");
    }

    @Test
    void testGetPopularTimezones() {
        String responseBody = webTestClient.get()
                .uri("/api/util/timezones/popular?language=tr")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        assertTrue(responseBody.contains("timezones"));
        assertTrue(responseBody.contains("totalCount"));
        assertTrue(responseBody.contains("offset"));

        System.out.println("✅ Popular timezones - OK");
    }

    @Test
    void testGetAllTimezones() {
        String responseBody = webTestClient.get()
                .uri("/api/util/timezones?language=en")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        assertTrue(responseBody.contains("timezones"));
        assertTrue(responseBody.contains("totalCount"));

        System.out.println("✅ All timezones - OK");
    }

    @Test
    void testGetHouseSystems() {
        String responseBody = webTestClient.get()
                .uri("/api/util/house-systems?language=en")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        System.out.println("\n=== RESPONSE BODY (House Systems) ===");
        System.out.println(responseBody);
        System.out.println("======================================\n");

        assertNotNull(responseBody);
        assertTrue(responseBody.contains("PLACIDUS"), "Should contain PLACIDUS");
        assertTrue(responseBody.contains("WHOLE_SIGN"), "Should contain WHOLE_SIGN");
        assertTrue(responseBody.contains("EQUAL"), "Should contain EQUAL");
        assertTrue(responseBody.contains("KOCH"), "Should contain KOCH");
        assertTrue(responseBody.contains("\"recommended\"") && responseBody.contains("true"), "Should have recommended:true");

        System.out.println("✅ House systems - OK");
    }

    @Test
    void testGetZodiacSystems() {
        String responseBody = webTestClient.get()
                .uri("/api/util/zodiac-systems?language=en")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        System.out.println("\n=== RESPONSE BODY (Zodiac Systems) ===");
        System.out.println(responseBody);
        System.out.println("=======================================\n");

        assertNotNull(responseBody);
        assertTrue(responseBody.contains("TROPICAL"), "Should contain TROPICAL");
        assertTrue(responseBody.contains("SIDEREAL"), "Should contain SIDEREAL");
        assertTrue(responseBody.contains("\"supported\"") && responseBody.contains("true"), "Should have supported:true");
        assertTrue(responseBody.contains("\"supported\"") && responseBody.contains("false"), "Should have supported:false");

        System.out.println("✅ Zodiac systems - OK");
    }

    @Test
    void testGetAspectTypes() {
        String responseBody = webTestClient.get()
                .uri("/api/util/aspect-types?language=tr")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        assertTrue(responseBody.contains("CONJUNCTION"));
        assertTrue(responseBody.contains("SEXTILE"));
        assertTrue(responseBody.contains("SQUARE"));
        assertTrue(responseBody.contains("TRINE"));
        assertTrue(responseBody.contains("OPPOSITION"));
        assertTrue(responseBody.contains("☌"));
        assertTrue(responseBody.contains("#9C27B0"));

        System.out.println("✅ Aspect types - OK");
    }

    @Test
    void testAspectTypesHaveColors() {
        String responseBody = webTestClient.get()
                .uri("/api/util/aspect-types?language=en")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        assertTrue(responseBody.contains("#9C27B0")); // Purple
        assertTrue(responseBody.contains("#4CAF50")); // Green
        assertTrue(responseBody.contains("#F44336")); // Red
        assertTrue(responseBody.contains("#2196F3")); // Blue
        assertTrue(responseBody.contains("#FF9800")); // Orange

        System.out.println("✅ Aspect colors - OK");
    }

    @Test
    void testHealthEndpoint() {
        String responseBody = webTestClient.get()
                .uri("/api/util/health")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertEquals("Utility API is running", responseBody);

        System.out.println("✅ Health endpoint - OK");
    }

    @Test
    void testAllEndpointsReturnJson() {
        webTestClient.get()
                .uri("/api/util/picker-options")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json");

        webTestClient.get()
                .uri("/api/util/timezones/popular")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json");

        System.out.println("✅ All endpoints return JSON - OK");
    }

    @Test
    void testDefaultLanguageIsEnglish() {
        String responseBody = webTestClient.get()
                .uri("/api/util/picker-options")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        System.out.println("\n=== RESPONSE BODY (Default Language) ===");
        System.out.println(responseBody);
        System.out.println("=========================================\n");

        assertNotNull(responseBody);
        assertTrue(responseBody.contains("\"language\"") && responseBody.contains("\"en\""), "Should contain language:en as default");

        System.out.println("✅ Default language (EN) - OK");
    }
}
