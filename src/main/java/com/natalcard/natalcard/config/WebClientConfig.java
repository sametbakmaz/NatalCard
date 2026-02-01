package com.natalcard.natalcard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient restCountriesWebClient(WebClient.Builder builder) {
        // REST Countries API - Free, reliable, no authentication required
        // Provides country and basic region data
        return builder
                .baseUrl("https://restcountries.com/v3.1")
                .build();
    }

    @Bean
    public WebClient geoNamesWebClient(WebClient.Builder builder) {
        // GeoNames API - Free with registration
        // Set GEONAMES_USERNAME environment variable
        return builder
                .baseUrl("http://api.geonames.org")
                .build();
    }

    @Bean
    public WebClient nominatimWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://nominatim.openstreetmap.org")
                .defaultHeader("User-Agent", "NatalCardApp/1.0 (astrology-app)")
                .build();
    }
}
