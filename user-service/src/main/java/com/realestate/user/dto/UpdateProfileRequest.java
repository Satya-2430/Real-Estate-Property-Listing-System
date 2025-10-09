package com.realestate.user.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String profileImage;
}