package com.realestate.admin.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ContactInquiryRequest {
    
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]{3,50}$", message = "Name must contain only alphabets and spaces, 3-50 characters")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Mobile number must start with 6/7/8/9 and be exactly 10 digits")
    private String phone;
    
    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(BUYER|SELLER|AGENT)$", message = "Role must be BUYER, SELLER, or AGENT")
    private String role;
    
    @NotBlank(message = "Message is required")
    @Size(min = 10, max = 500, message = "Message must be between 10-500 characters")
    private String message;
}