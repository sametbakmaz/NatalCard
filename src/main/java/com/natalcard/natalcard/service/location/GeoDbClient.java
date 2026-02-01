package com.natalcard.natalcard.service.location;

import com.fasterxml.jackson.databind.JsonNode;
import com.natalcard.natalcard.api.dto.CountryDTO;
import com.natalcard.natalcard.api.dto.RegionDTO;
import com.natalcard.natalcard.api.dto.PlaceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

/**
 * REST Countries API Client (FREE, reliable, no authentication)
 * Provides country data and basic location information
 * API: https://restcountries.com
 */
@Component
@Slf4j
public class GeoDbClient {

    private final WebClient restCountriesClient;
    private final WebClient geoNamesClient;

    public GeoDbClient(
            @Qualifier("restCountriesWebClient") WebClient restCountriesWebClient,
            @Qualifier("geoNamesWebClient") WebClient geoNamesWebClient) {
        this.restCountriesClient = restCountriesWebClient;
        this.geoNamesClient = geoNamesWebClient;
    }

    /**
     * Get all countries from REST Countries API
     * API: GET https://restcountries.com/v3.1/all
     */
    public List<CountryDTO> getCountries() {
        try {
            JsonNode[] response = restCountriesClient.get()
                    .uri("/all?fields=cca2,name,flag")
                    .retrieve()
                    .bodyToMono(JsonNode[].class)
                    .block();

            List<CountryDTO> countries = new ArrayList<>();
            if (response != null) {
                for (JsonNode node : response) {
                    String code = node.get("cca2").asText(); // 2-letter code (TR, US, etc.)
                    String commonName = node.path("name").path("common").asText();
                    String flag = node.has("flag") ? node.get("flag").asText() : null;

                    countries.add(new CountryDTO(code, commonName, flag));
                }
            }

            // Sort by name
            countries.sort(java.util.Comparator.comparing(CountryDTO::getName));
            log.info("Fetched {} countries from REST Countries API", countries.size());
            return countries;
        } catch (Exception e) {
            log.error("Error fetching countries from REST Countries: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Get regions (states/provinces) for a country using GeoNames
     * Requires GEONAMES_USERNAME environment variable
     * Fallback: Returns empty list if GeoNames is not configured
     */
    public List<RegionDTO> getRegions(String countryCode) {
        String username = System.getenv("GEONAMES_USERNAME");
        if (username == null || username.isEmpty()) {
            log.warn("GEONAMES_USERNAME not set - regions unavailable. Get free account at https://www.geonames.org/login");
            return new ArrayList<>();
        }

        try {
            // Get country info first to get geonameId
            JsonNode searchResult = geoNamesClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/searchJSON")
                            .queryParam("country", countryCode)
                            .queryParam("featureCode", "PCLI") // Country
                            .queryParam("maxRows", "1")
                            .queryParam("username", username)
                            .build())
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            if (searchResult == null || !searchResult.has("geonames") ||
                searchResult.get("geonames").isEmpty()) {
                log.warn("Country {} not found in GeoNames", countryCode);
                return new ArrayList<>();
            }

            int countryGeonameId = searchResult.get("geonames").get(0).get("geonameId").asInt();

            // Get admin divisions (states/provinces)
            JsonNode children = geoNamesClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/childrenJSON")
                            .queryParam("geonameId", countryGeonameId)
                            .queryParam("maxRows", "100")
                            .queryParam("username", username)
                            .build())
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            List<RegionDTO> regions = new ArrayList<>();
            if (children != null && children.has("geonames")) {
                for (JsonNode node : children.get("geonames")) {
                    String adminCode = node.has("adminCode1") ? node.get("adminCode1").asText() :
                                      node.get("geonameId").asText();
                    String name = node.get("name").asText();

                    regions.add(new RegionDTO(
                            adminCode,
                            name,
                            countryCode,
                            adminCode,
                            null
                    ));
                }
            }

            log.info("Fetched {} regions for {} from GeoNames", regions.size(), countryCode);
            return regions;
        } catch (Exception e) {
            log.error("Error fetching regions for {}: {}", countryCode, e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Get cities for a country/region using GeoNames
     * Requires GEONAMES_USERNAME environment variable
     */
    public List<PlaceDTO> getCities(String countryCode, String regionCode) {
        String username = System.getenv("GEONAMES_USERNAME");
        if (username == null || username.isEmpty()) {
            log.warn("GEONAMES_USERNAME not set - cities unavailable");
            return new ArrayList<>();
        }

        try {
            var uriBuilder = geoNamesClient.get()
                    .uri(builder -> {
                        builder.path("/searchJSON")
                               .queryParam("country", countryCode)
                               .queryParam("featureClass", "P") // Populated places
                               .queryParam("maxRows", "100")
                               .queryParam("username", username)
                               .queryParam("orderby", "population");

                        if (regionCode != null && !regionCode.isEmpty()) {
                            builder.queryParam("adminCode1", regionCode);
                        }

                        return builder.build();
                    });

            JsonNode response = uriBuilder
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            List<PlaceDTO> places = new ArrayList<>();
            if (response != null && response.has("geonames")) {
                for (JsonNode node : response.get("geonames")) {
                    places.add(PlaceDTO.builder()
                            .id("GEONAMES:" + node.get("geonameId").asText())
                            .name(node.get("name").asText())
                            .countryCode(node.get("countryCode").asText())
                            .regionCode(node.has("adminCode1") ? node.get("adminCode1").asText() : null)
                            .type(PlaceDTO.PlaceType.CITY)
                            .latitude(node.get("lat").asDouble())
                            .longitude(node.get("lng").asDouble())
                            .timezoneId(node.has("timezone") ? node.get("timezone").path("timeZoneId").asText() : null)
                            .build());
                }
            }

            log.info("Fetched {} cities for {}/{} from GeoNames", places.size(), countryCode, regionCode);
            return places;
        } catch (Exception e) {
            log.error("Error fetching cities for {}/{}: {}", countryCode, regionCode, e.getMessage());
            return new ArrayList<>();
        }
    }
}
