package com.natalcard.natalcard.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.natalcard.natalcard.api.dto.NatalChartRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.cache.type=none") // Disable cache for tests
class NatalChartApiIntegrationTest {

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        // Create WebTestClient using the dynamic port from Spring Boot test
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void testNatalChartCalculationAndLogOutput() throws Exception {
        NatalChartRequestDTO request = NatalChartRequestDTO.builder()
                .birthDateTimeLocal("1996-04-23T14:35:00")
                .timeZoneId("Europe/Istanbul")
                .latitude(40.983)
                .longitude(29.029)
                .zodiac("TROPICAL")
                .houseSystem("PLACIDUS")
                .includeAspects(true)
                .build();

        String responseBody = webTestClient.post()
                .uri("/api/astro/natal-chart")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        System.out.println("\n========================================");
        System.out.println("   NATAL CHART CALCULATION RESULT");
        System.out.println("========================================");
        System.out.println("\nFull Response:");
        System.out.println(responseBody);

        Map<?,?> map = objectMapper.readValue(responseBody, Map.class);

        System.out.println("\n--- Extracted Data ---");
        System.out.println("Meta: " + map.get("meta"));
        System.out.println("\nAngles: " + map.get("angles"));

        @SuppressWarnings("unchecked")
        Map<String, Object> points = (Map<String, Object>) map.get("points");
        if (points != null) {
            System.out.println("\nPlanets:");
            System.out.println("  Sun: " + points.get("SUN"));
            System.out.println("  Moon: " + points.get("MOON"));
            System.out.println("  Mercury: " + points.get("MERCURY"));
            System.out.println("  Venus: " + points.get("VENUS"));
            System.out.println("  Mars: " + points.get("MARS"));
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> meta = (Map<String, Object>) map.get("meta");
        if (meta != null) {
            System.out.println("\nWarnings: " + meta.get("warnings"));
        }

        System.out.println("\nAspects: " + map.get("aspects"));
        System.out.println("\n========================================\n");
    }
}
