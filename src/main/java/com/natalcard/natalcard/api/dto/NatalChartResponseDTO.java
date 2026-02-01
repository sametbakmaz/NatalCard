package com.natalcard.natalcard.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NatalChartResponseDTO {

    private MetaDTO meta;
    private AnglesDTO angles;
    private List<HouseDTO> houses;
    private Map<String, PlanetPointDTO> points;
    private List<AspectDTO> aspects;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MetaDTO {
        private String requestedHouseSystem;
        private String effectiveHouseSystem;
        private List<String> warnings;

        // Localized fields
        private String requestedHouseSystemLocalized;
        private String effectiveHouseSystemLocalized;
        private List<String> warningsLocalized;

        public List<String> getWarnings() {
            return warnings == null ? new ArrayList<>() : warnings;
        }

        public List<String> getWarningsLocalized() {
            return warningsLocalized == null ? new ArrayList<>() : warningsLocalized;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnglesDTO {
        private Double ascendantLongitude;
        private Double midHeavenLongitude;
        private String ascendantSign;
        private String midHeavenSign;

        // Localized fields
        private String ascendantSignLocalized;
        private String midHeavenSignLocalized;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HouseDTO {
        private Integer number;
        private Double cuspLongitude;
        private String sign;

        // Localized field
        private String signLocalized;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlanetPointDTO {
        private String name;
        private Double longitude;
        private String sign;
        private Integer house;
        private Double signDegree; // Degree within the sign (0-30)

        // Localized fields
        private String nameLocalized;
        private String signLocalized;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AspectDTO {
        private String planet1;
        private String planet2;
        private String aspectType;
        private Double angle;
        private Double orb;
        private Boolean isApplying;

        // Localized fields
        private String planet1Localized;
        private String planet2Localized;
        private String aspectTypeLocalized;
    }
}
