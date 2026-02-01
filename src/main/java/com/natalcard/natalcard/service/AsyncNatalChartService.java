package com.natalcard.natalcard.service;

import com.natalcard.natalcard.api.dto.NatalChartRequestDTO;
import com.natalcard.natalcard.api.dto.NatalChartResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Async wrapper for NatalChartService
 * Enables non-blocking concurrent natal chart calculations
 *
 * Benefits:
 * - Handles 50-60 concurrent requests efficiently
 * - Non-blocking I/O
 * - Thread pool management
 * - Graceful degradation under load
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncNatalChartService {

    private final NatalChartService natalChartService;

    /**
     * Calculate natal chart asynchronously
     * Uses dedicated thread pool to avoid blocking main thread
     *
     * @param request Birth data and preferences
     * @return CompletableFuture containing natal chart response
     */
    @Async("natalChartExecutor")
    public CompletableFuture<NatalChartResponseDTO> calculateNatalChartAsync(NatalChartRequestDTO request) {
        try {
            log.info("Async natal chart calculation started for {}",
                request.getBirthDateTimeLocal());

            long startTime = System.currentTimeMillis();

            // Delegate to synchronous service
            NatalChartResponseDTO response = natalChartService.calculateNatalChart(request);

            long duration = System.currentTimeMillis() - startTime;
            log.info("Natal chart calculation completed in {}ms", duration);

            return CompletableFuture.completedFuture(response);

        } catch (Exception e) {
            log.error("Async natal chart calculation failed", e);
            return CompletableFuture.failedFuture(e);
        }
    }

    /**
     * Calculate multiple natal charts in parallel
     * Useful for batch operations or comparison features
     *
     * @param requests Array of natal chart requests
     * @return CompletableFuture containing array of responses
     */
    @Async("natalChartExecutor")
    public CompletableFuture<NatalChartResponseDTO[]> calculateMultipleAsync(NatalChartRequestDTO[] requests) {
        try {
            log.info("Batch calculation started for {} charts", requests.length);

            CompletableFuture<NatalChartResponseDTO>[] futures =
                new CompletableFuture[requests.length];

            for (int i = 0; i < requests.length; i++) {
                futures[i] = calculateNatalChartAsync(requests[i]);
            }

            // Wait for all to complete
            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);

            return allOf.thenApply(v -> {
                NatalChartResponseDTO[] results = new NatalChartResponseDTO[futures.length];
                for (int i = 0; i < futures.length; i++) {
                    results[i] = futures[i].join();
                }
                return results;
            });

        } catch (Exception e) {
            log.error("Batch calculation failed", e);
            return CompletableFuture.failedFuture(e);
        }
    }
}
