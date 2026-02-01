package com.natalcard.natalcard.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDTO {
    private String id; // Format: GEODB:x | GEONAMES:y | NOMINATIM:lat,lon
    private String name;
    private String countryCode;
    private String regionCode;
    private PlaceType type;
    private Double latitude;
    private Double longitude;
    private String timezoneId;

    public enum PlaceType {
        CITY, DISTRICT
    }
}
