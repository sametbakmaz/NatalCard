package com.natalcard.natalcard.performance;

import com.natalcard.natalcard.api.dto.NatalChartRequestDTO;
import com.natalcard.natalcard.api.dto.NatalChartResponseDTO;
import com.natalcard.natalcard.service.AsyncNatalChartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Performance tests for async natal chart calculations
 * Tests ability to handle 50-60 concurrent requests
 */
@SpringBootTest
class AsyncPerformanceTest {

    @Autowired
    private AsyncNatalChartService asyncService;

    @Test
    void testConcurrent50Requests() throws Exception {
        System.out.println("\n=== Testing 50 Concurrent Requests ===\n");

        List<CompletableFuture<NatalChartResponseDTO>> futures = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        // Create 50 concurrent requests
        for (int i = 0; i < 50; i++) {
            NatalChartRequestDTO request = createTestRequest(i);
            CompletableFuture<NatalChartResponseDTO> future =
                asyncService.calculateNatalChartAsync(request);
            futures.add(future);
        }

        // Wait for all to complete
        CompletableFuture<Void> allOf = CompletableFuture.allOf(
            futures.toArray(new CompletableFuture[0])
        );

        allOf.get(30, TimeUnit.SECONDS); // Max 30 seconds timeout

        long duration = System.currentTimeMillis() - startTime;

        // Verify all completed successfully
        int successCount = 0;
        for (CompletableFuture<NatalChartResponseDTO> future : futures) {
            assertTrue(future.isDone());
            assertFalse(future.isCompletedExceptionally());
            assertNotNull(future.get());
            successCount++;
        }

        System.out.println("✅ All 50 requests completed successfully");
        System.out.println("⏱️  Total time: " + duration + "ms");
        System.out.println("📊 Average time per request: " + (duration / 50) + "ms");
        System.out.println("🚀 Throughput: " + (50000.0 / duration) + " requests/second\n");

        assertEquals(50, successCount);
    }

    @Test
    void testConcurrent60Requests() throws Exception {
        System.out.println("\n=== Testing 60 Concurrent Requests ===\n");

        List<CompletableFuture<NatalChartResponseDTO>> futures = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        // Create 60 concurrent requests
        for (int i = 0; i < 60; i++) {
            NatalChartRequestDTO request = createTestRequest(i);
            CompletableFuture<NatalChartResponseDTO> future =
                asyncService.calculateNatalChartAsync(request);
            futures.add(future);
        }

        // Wait for all to complete
        CompletableFuture<Void> allOf = CompletableFuture.allOf(
            futures.toArray(new CompletableFuture[0])
        );

        allOf.get(60, TimeUnit.SECONDS); // Max 60 seconds timeout

        long duration = System.currentTimeMillis() - startTime;

        // Verify all completed successfully
        int successCount = 0;
        for (CompletableFuture<NatalChartResponseDTO> future : futures) {
            assertTrue(future.isDone());
            if (!future.isCompletedExceptionally()) {
                assertNotNull(future.get());
                successCount++;
            }
        }

        System.out.println("✅ " + successCount + "/60 requests completed successfully");
        System.out.println("⏱️  Total time: " + duration + "ms");
        System.out.println("📊 Average time per request: " + (duration / 60) + "ms");
        System.out.println("🚀 Throughput: " + (60000.0 / duration) + " requests/second\n");

        assertTrue(successCount >= 50, "At least 50 requests should succeed");
    }

    @Test
    void testBatchCalculation() throws Exception {
        System.out.println("\n=== Testing Batch Calculation (10 charts) ===\n");

        NatalChartRequestDTO[] requests = new NatalChartRequestDTO[10];
        for (int i = 0; i < 10; i++) {
            requests[i] = createTestRequest(i);
        }

        long startTime = System.currentTimeMillis();

        CompletableFuture<NatalChartResponseDTO[]> future =
            asyncService.calculateMultipleAsync(requests);

        NatalChartResponseDTO[] results = future.get(30, TimeUnit.SECONDS);

        long duration = System.currentTimeMillis() - startTime;

        assertNotNull(results);
        assertEquals(10, results.length);

        for (NatalChartResponseDTO result : results) {
            assertNotNull(result);
            assertNotNull(result.getPoints());
            assertTrue(result.getPoints().containsKey("SUN"));
        }

        System.out.println("✅ All 10 batch calculations completed");
        System.out.println("⏱️  Total time: " + duration + "ms");
        System.out.println("📊 Average time per chart: " + (duration / 10) + "ms\n");
    }

    private NatalChartRequestDTO createTestRequest(int seed) {
        // Vary birth data slightly to avoid cache hits
        int day = 1 + (seed % 28);
        int hour = seed % 24;
        int minute = (seed * 15) % 60;

        return NatalChartRequestDTO.builder()
            .birthDateTimeLocal(String.format("1996-04-%02d T%02d:%02d:00", day, hour, minute))
            .timeZoneId("Europe/Istanbul")
            .latitude(40.983 + (seed * 0.001))
            .longitude(29.029 + (seed * 0.001))
            .zodiac("TROPICAL")
            .houseSystem("WHOLE_SIGN")
            .includeAspects(true)
            .language("en")
            .build();
    }
}
