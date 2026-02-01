package com.natalcard.natalcard.service.location;

import com.natalcard.natalcard.api.dto.CountryDTO;
import com.natalcard.natalcard.api.dto.PlaceDTO;
import com.natalcard.natalcard.api.dto.RegionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Main location service orchestrating multiple providers
 * Priority: GeoDB (primary) → GeoNames (districts)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {

    private final GeoDbClient geoDbClient;
    private final GeoNamesClient geoNamesClient;

    /**
     * Get all countries
     */
    public List<CountryDTO> getCountries() {
        List<CountryDTO> countries = geoDbClient.getCountries();
        if (countries.isEmpty()) {
            log.warn("GeoDB returned no countries");
        }
        return countries;
    }

    /**
     * Get regions for a country
     */
    public List<RegionDTO> getRegions(String countryCode) {
        List<RegionDTO> regions = geoDbClient.getRegions(countryCode);
        if (regions.isEmpty()) {
            log.warn("GeoDB returned no regions for country: {}", countryCode);
        }
        return regions;
    }

    /**
     * Get places (cities or districts) for a country/region
     */
    public List<PlaceDTO> getPlaces(String countryCode, String regionCode, PlaceDTO.PlaceType level) {
        if (level == PlaceDTO.PlaceType.CITY) {
            return geoDbClient.getCities(countryCode, regionCode);
        } else {
            // For districts, try GeoNames
            List<PlaceDTO> districts = geoNamesClient.getDistricts(countryCode, regionCode);
            if (districts.isEmpty()) {
                log.warn("GeoNames returned no districts for {}/{}", countryCode, regionCode);
            }
            return districts;
        }
    }

    /**
     * Get place detail by ID
     */
    public PlaceDTO getPlaceById(String placeId) {
        if (placeId.startsWith("GEONAMES:")) {
            String geonameId = placeId.substring(9);
            return geoNamesClient.getPlaceDetail(geonameId);
        }

        // For GeoDB, we would need to implement detail endpoints
        log.warn("Place detail not implemented for ID: {}", placeId);
        return null;
    }

    /**
     * Search places by query - uses GeoDB
     */
    public List<PlaceDTO> searchPlaces(String query, String countryCode) {
        // Try GeoDB for search
        List<PlaceDTO> results = new ArrayList<>();

        if (countryCode != null && !countryCode.isEmpty()) {
            // Get cities from GeoDB for the specified country
            List<CountryDTO> countries = getCountries();
            for (CountryDTO country : countries) {
                if (country.getCode().equalsIgnoreCase(countryCode)) {
                    // Search by getting all cities for this country
                    List<RegionDTO> regions = getRegions(countryCode);
                    for (RegionDTO region : regions) {
                        results.addAll(geoDbClient.getCities(countryCode, region.getCode()));
                    }
                    break;
                }
            }
        }

        if (results.isEmpty()) {
            log.warn("No results found for query: {}", query);
        }

        return results;
    }

    /**
     * Reverse geocode (lat/lon to place) - not fully implemented without Nominatim
     */
    public PlaceDTO reverseGeocode(double latitude, double longitude) {
        log.warn("Reverse geocoding not implemented");
        return null;
    }
}
