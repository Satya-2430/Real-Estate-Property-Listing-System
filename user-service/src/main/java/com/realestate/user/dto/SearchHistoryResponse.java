package com.realestate.user.dto;

import lombok.Data;

@Data
public class SearchHistoryResponse {
    private Long id;
    private Long userId;
    private String filters;
    private String timestamp;
}