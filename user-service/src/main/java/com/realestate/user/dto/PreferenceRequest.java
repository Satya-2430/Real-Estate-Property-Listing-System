package com.realestate.user.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PreferenceRequest {
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String propertyType;
    private Integer minBedrooms;
    private Integer maxBedrooms;
    private String preferredLocation;
}