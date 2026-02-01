package com.natalcard.natalcard.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionDTO {
    private String code;
    private String name;
    private String countryCode;
    private String isoCode;
    private String fipsCode;
}
