package com.realestate.user.dto;

import lombok.Data;

@Data
public class ProfileUpdateRequest {
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
}