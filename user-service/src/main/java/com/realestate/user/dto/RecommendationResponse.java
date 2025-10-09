package com.realestate.user.dto;

import lombok.Data;

@Data
public class RecommendationResponse {
    private Long id;
    private Long userId;
    private Long propertyId;
    private Double score;
    private String reason;
}