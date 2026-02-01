package com.natalcard.natalcard.api;

import com.natalcard.natalcard.api.dto.CountryDTO;
import com.natalcard.natalcard.api.dto.PlaceDTO;
import com.natalcard.natalcard.api.dto.RegionDTO;
import com.natalcard.natalcard.service.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for location services
 */
@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    /**
     * Get all countries
     * GET /api/location/countries
     */
    @GetMapping("/countries")
    public ResponseEntity<?> getCountries() {
        List<CountryDTO> countries = locationService.getCountries();
        return ResponseEntity.ok(countries);
    }

    /**
     * Get regions for a country
     * GET /api/location/countries/{code}/regions
     */
    @GetMapping("/countries/{code}/regions")
    public ResponseEntity<?> getRegions(@PathVariable String code) {
        List<RegionDTO> regions = locationService.getRegions(code);
        return ResponseEntity.ok(regions);
    }

    /**
     * Get places (cities or districts) for a country/region
     * GET /api/location/countries/{code}/regions/{region}/places?level=CITY|DISTRICT
     */
    @GetMapping("/countries/{code}/regions/{region}/places")
    public ResponseEntity<?> getPlaces(
            @PathVariable String code,
            @PathVariable String region,
            @RequestParam(defaultValue = "CITY") PlaceDTO.PlaceType level) {

        List<PlaceDTO> places = locationService.getPlaces(code, region, level);
        return ResponseEntity.ok(places);
    }

    /**
     * Get place detail by ID
     * GET /api/location/places/{placeId}
     */
    @GetMapping("/places/{placeId}")
    public ResponseEntity<?> getPlaceById(@PathVariable String placeId) {
        PlaceDTO place = locationService.getPlaceById(placeId);
        if (place == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(place);
    }

    /**
     * Search places by query
     * GET /api/location/geocode?q=Istanbul&countryCode=TR
     */
    @GetMapping("/geocode")
    public ResponseEntity<?> searchPlaces(
            @RequestParam String q,
            @RequestParam(required = false) String countryCode) {

        List<PlaceDTO> places = locationService.searchPlaces(q, countryCode);
        if (places.isEmpty()) {
            return ResponseEntity.status(422).build(); // Unprocessable Entity
        }
        return ResponseEntity.ok(places);
    }

    /**
     * Reverse geocode (lat/lon to place)
     * GET /api/location/reverse?lat=40.983&lon=29.029
     */
    @GetMapping("/reverse")
    public ResponseEntity<?> reverseGeocode(
            @RequestParam double lat,
            @RequestParam double lon) {

        PlaceDTO place = locationService.reverseGeocode(lat, lon);
        if (place == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(place);
    }
}
