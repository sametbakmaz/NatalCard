package com.natalcard.natalcard.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTOs for utility endpoints (pickers, selectors, etc.)
 */
public class UtilityDTOs {

    /**
     * Timezone information for picker
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimezoneDTO {
        private String id;                    // e.g., "Europe/Istanbul"
        private String displayName;           // e.g., "Turkey Time"
        private String displayNameLocalized;  // e.g., "Türkiye Saati"
        private String offset;                // e.g., "+03:00"
        private String region;                // e.g., "Europe"
        private String country;               // e.g., "Turkey"
        private String countryLocalized;      // e.g., "Türkiye"
    }

    /**
     * Zodiac system information
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ZodiacSystemDTO {
        private String code;                  // e.g., "TROPICAL"
        private String name;                  // e.g., "Tropical"
        private String nameLocalized;         // e.g., "Tropikal"
        private String description;           // e.g., "Based on seasons"
        private String descriptionLocalized;  // Localized description
        private boolean supported;            // Currently only TROPICAL
    }

    /**
     * House system information
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HouseSystemDTO {
        private String code;                  // e.g., "PLACIDUS"
        private String name;                  // e.g., "Placidus"
        private String nameLocalized;         // e.g., "Placidus"
        private String description;           // e.g., "Most popular system"
        private String descriptionLocalized;  // Localized description
        private boolean recommended;          // true for PLACIDUS
        private String bestFor;               // e.g., "Most latitudes"
        private String bestForLocalized;      // Localized
    }

    /**
     * Aspect type information
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AspectTypeDTO {
        private String code;                  // e.g., "TRINE"
        private String name;                  // e.g., "Trine"
        private String nameLocalized;         // e.g., "Trigon"
        private double angle;                 // e.g., 120.0
        private String symbol;                // e.g., "△"
        private String meaning;               // e.g., "Harmonious"
        private String meaningLocalized;      // e.g., "Uyumlu"
        private String color;                 // e.g., "#4CAF50" (for UI)
    }

    /**
     * Location coordinates response
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CoordinatesDTO {
        private double latitude;
        private double longitude;
        private String placeId;
        private String placeName;
        private String placeNameLocalized;
        private String countryCode;
        private String regionCode;
        private String timezoneId;            // Suggested timezone
    }

    /**
     * Picker options response wrapper
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PickerOptionsResponse {
        private List<ZodiacSystemDTO> zodiacSystems;
        private List<HouseSystemDTO> houseSystems;
        private List<AspectTypeDTO> aspectTypes;
        private String language;
    }

    /**
     * Timezone list response
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimezoneListResponse {
        private List<TimezoneDTO> timezones;
        private int totalCount;
        private String language;
    }
}
