package com.natalcard.natalcard.api;

import com.natalcard.natalcard.api.dto.NatalChartRequestDTO;
import com.natalcard.natalcard.api.dto.NatalChartResponseDTO;
import com.natalcard.natalcard.service.AsyncNatalChartService;
import com.natalcard.natalcard.service.NatalChartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

/**
 * REST Controller for natal chart calculations
 * Supports both synchronous and asynchronous processing
 */
@RestController
@RequestMapping("/api/astro")
@RequiredArgsConstructor
public class NatalChartController {

    private final NatalChartService natalChartService;
    private final AsyncNatalChartService asyncNatalChartService;

    /**
     * Calculate natal chart (synchronous)
     * Use for single requests or when immediate response is needed
     *
     * POST /api/astro/natal-chart
     */
    @PostMapping("/natal-chart")
    public ResponseEntity<?> calculateNatalChart(
            @Valid @RequestBody NatalChartRequestDTO request) {

        try {
            NatalChartResponseDTO response = natalChartService.calculateNatalChart(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Calculate natal chart (asynchronous)
     * Recommended for high-load scenarios (50-60 concurrent requests)
     * Returns CompletableFuture - non-blocking
     *
     * POST /api/astro/natal-chart/async
     */
    @PostMapping("/natal-chart/async")
    public CompletableFuture<ResponseEntity<NatalChartResponseDTO>> calculateNatalChartAsync(
            @Valid @RequestBody NatalChartRequestDTO request) {

        return asyncNatalChartService.calculateNatalChartAsync(request)
            .thenApply(ResponseEntity::ok)
            .exceptionally(ex -> ResponseEntity.internalServerError().build());
    }

    /**
     * Calculate multiple natal charts in parallel
     * Useful for batch operations or comparison features
     *
     * POST /api/astro/natal-chart/batch
     */
    @PostMapping("/natal-chart/batch")
    public CompletableFuture<ResponseEntity<NatalChartResponseDTO[]>> calculateBatch(
            @Valid @RequestBody NatalChartRequestDTO[] requests) {

        if (requests.length == 0) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        }

        if (requests.length > 10) {
            // Limit batch size to prevent abuse
            return CompletableFuture.completedFuture(
                ResponseEntity.badRequest()
                    .body(null)  // Could add error message DTO
            );
        }

        return asyncNatalChartService.calculateMultipleAsync(requests)
            .thenApply(ResponseEntity::ok)
            .exceptionally(ex -> ResponseEntity.internalServerError().build());
    }
}

