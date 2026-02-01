package com.natalcard.natalcard.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NatalChartRequestDTO {

    @NotBlank(message = "Birth date/time is required")
    private String birthDateTimeLocal; // ISO format: 1996-04-23T14:35:00

    @NotBlank(message = "Timezone is required")
    private String timeZoneId; // e.g., Europe/Istanbul

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    private String zodiac = "TROPICAL"; // Only TROPICAL supported

    private String houseSystem = "PLACIDUS"; // PLACIDUS, EQUAL, or WHOLE_SIGN

    private Boolean includeAspects = true;

    private String language = "en"; // Language for translations: "en" or "tr"
}
