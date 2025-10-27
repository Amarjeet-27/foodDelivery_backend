package com.project.foodDelivery.dto;

import com.project.foodDelivery.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}
