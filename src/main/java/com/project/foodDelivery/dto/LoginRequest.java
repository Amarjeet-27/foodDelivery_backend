package com.project.foodDelivery.dto;

import com.project.foodDelivery.model.Role;
import lombok.Data;

@Data
public class LoginRequest {
        private String email;
        private String password;
}
