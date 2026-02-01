package com.natalcard.natalcard.service.location;

import com.natalcard.natalcard.api.dto.PlaceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Nominatim/OpenStreetMap Client (FALLBACK location provider)
 * NOTE: Disabled pending Maven sync for JsonNode support
 */
@Component
@Slf4j
public class NominatimClient {

    private final WebClient webClient;

    public NominatimClient(@Qualifier("nominatimWebClient") WebClient nominatimWebClient) {
        this.webClient = nominatimWebClient;
    }

    /**
     * Search places - DISABLED pending Maven sync
     */
    public List<PlaceDTO> searchPlaces(String query, String countryCode) {
        log.warn("searchPlaces not available");
        return new ArrayList<>();
    }

    /**
     * Reverse geocode - DISABLED pending Maven sync
     */
    public PlaceDTO reverseGeocode(double latitude, double longitude) {
        log.warn("reverseGeocode not available");
        return null;
    }
}
