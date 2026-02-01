package com.natalcard.natalcard.api;

import com.natalcard.natalcard.api.dto.UtilityDTOs.*;
import com.natalcard.natalcard.service.UtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for utility endpoints
 * Provides picker data for mobile/web UI components
 *
 * Endpoints:
 * - GET /api/util/timezones - All timezones
 * - GET /api/util/timezones/popular - Popular timezones
 * - GET /api/util/zodiac-systems - Zodiac systems
 * - GET /api/util/house-systems - House systems
 * - GET /api/util/aspect-types - Aspect types
 * - GET /api/util/picker-options - All options in one call
 */
@RestController
@RequestMapping("/api/util")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow CORS for mobile apps
public class UtilityController {

    private final UtilityService utilityService;

    /**
     * Get all available timezones
     *
     * GET /api/util/timezones?language=tr
     *
     * Returns ~400 timezones grouped by region
     * Use for timezone picker dropdown
     */
    @GetMapping("/timezones")
    public ResponseEntity<TimezoneListResponse> getAllTimezones(
            @RequestParam(defaultValue = "en") String language) {

        TimezoneListResponse response = utilityService.getAllTimezones(language);
        return ResponseEntity.ok(response);
    }

    /**
     * Get popular timezones (recommended)
     *
     * GET /api/util/timezones/popular?language=tr
     *
     * Returns ~12 most commonly used timezones
     * Better UX for timezone picker
     */
    @GetMapping("/timezones/popular")
    public ResponseEntity<TimezoneListResponse> getPopularTimezones(
            @RequestParam(defaultValue = "en") String language) {

        TimezoneListResponse response = utilityService.getPopularTimezones(language);
        return ResponseEntity.ok(response);
    }

    /**
     * Get zodiac systems
     *
     * GET /api/util/zodiac-systems?language=tr
     *
     * Returns: TROPICAL (supported), SIDEREAL (not supported)
     */
    @GetMapping("/zodiac-systems")
    public ResponseEntity<List<ZodiacSystemDTO>> getZodiacSystems(
            @RequestParam(defaultValue = "en") String language) {

        List<ZodiacSystemDTO> systems = utilityService.getZodiacSystems(language);
        return ResponseEntity.ok(systems);
    }

    /**
     * Get house systems
     *
     * GET /api/util/house-systems?language=tr
     *
     * Returns: PLACIDUS, WHOLE_SIGN, EQUAL, KOCH
     */
    @GetMapping("/house-systems")
    public ResponseEntity<List<HouseSystemDTO>> getHouseSystems(
            @RequestParam(defaultValue = "en") String language) {

        List<HouseSystemDTO> systems = utilityService.getHouseSystems(language);
        return ResponseEntity.ok(systems);
    }

    /**
     * Get aspect types
     *
     * GET /api/util/aspect-types?language=tr
     *
     * Returns: CONJUNCTION, SEXTILE, SQUARE, TRINE, OPPOSITION
     * Includes angle, symbol, meaning, and color for UI
     */
    @GetMapping("/aspect-types")
    public ResponseEntity<List<AspectTypeDTO>> getAspectTypes(
            @RequestParam(defaultValue = "en") String language) {

        List<AspectTypeDTO> types = utilityService.getAspectTypes(language);
        return ResponseEntity.ok(types);
    }

    /**
     * Get all picker options in one call (recommended)
     *
     * GET /api/util/picker-options?language=tr
     *
     * Returns zodiac systems, house systems, and aspect types
     * Reduces API calls for app initialization
     */
    @GetMapping("/picker-options")
    public ResponseEntity<PickerOptionsResponse> getAllPickerOptions(
            @RequestParam(defaultValue = "en") String language) {

        PickerOptionsResponse response = utilityService.getAllPickerOptions(language);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     *
     * GET /api/util/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Utility API is running");
    }
}
