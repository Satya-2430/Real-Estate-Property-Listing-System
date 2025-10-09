package com.realestate.admin.dto;

import com.realestate.admin.entity.InquiryStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContactInquiryResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private String subject;
    private String message;
    private InquiryStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime respondedAt;
    private String adminResponse;
}