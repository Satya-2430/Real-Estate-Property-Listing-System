package com.realestate.user.dto;

import com.realestate.user.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String contactNumber;
    private Role role;
}