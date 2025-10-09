package com.realestate.user.dto;

import com.realestate.user.entity.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String profileImage;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String companyName;
    private String about;
    private Role role;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private String token;
}