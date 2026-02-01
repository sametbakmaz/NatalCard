package com.natalcard.natalcard.service.location;

import com.fasterxml.jackson.databind.JsonNode;
import com.natalcard.natalcard.api.dto.PlaceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

/**
 * GeoNames API Client (for district-level data)
 * Requires GEONAMES_USERNAME environment variable
 */
@Component
@Slf4j
public class GeoNamesClient {

    private final WebClient webClient;

    public GeoNamesClient(@Qualifier("geoNamesWebClient") WebClient geoNamesWebClient) {
        this.webClient = geoNamesWebClient;
    }

    @Value("${geonames.username:demo}")
    private String username;

    public List<PlaceDTO> getDistricts(String countryCode, String adminCode1) {
        try {
            JsonNode response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/searchJSON")
                            .queryParam("country", countryCode)
                            .queryParam("adminCode1", adminCode1)
                            .queryParam("featureClass", "P")
                            .queryParam("maxRows", 100)
                            .queryParam("username", username)
                            .build())
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
                            .type(PlaceDTO.PlaceType.DISTRICT)
                            .latitude(node.get("lat").asDouble())
                            .longitude(node.get("lng").asDouble())
                            .timezoneId(node.has("timezone") && node.get("timezone").has("timeZoneId")
                                    ? node.get("timezone").get("timeZoneId").asText() : null)
                            .build());
                }
            }
            return places;
        } catch (Exception e) {
            log.error("Error fetching districts from GeoNames: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    public PlaceDTO getPlaceDetail(String geonameId) {
        try {
            JsonNode response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/getJSON")
                            .queryParam("geonameId", geonameId)
                            .queryParam("username", username)
                            .build())
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            if (response != null) {
                return PlaceDTO.builder()
                        .id("GEONAMES:" + response.get("geonameId").asText())
                        .name(response.get("name").asText())
                        .countryCode(response.get("countryCode").asText())
                        .regionCode(response.has("adminCode1") ? response.get("adminCode1").asText() : null)
                        .type(PlaceDTO.PlaceType.DISTRICT)
                        .latitude(Double.parseDouble(response.get("lat").asText()))
                        .longitude(Double.parseDouble(response.get("lng").asText()))
                        .timezoneId(response.has("timezone") && response.get("timezone").has("timeZoneId")
                                ? response.get("timezone").get("timeZoneId").asText() : null)
                        .build();
            }
        } catch (Exception e) {
            log.error("Error fetching place detail from GeoNames: {}", e.getMessage());
        }
        return null;
    }
}
