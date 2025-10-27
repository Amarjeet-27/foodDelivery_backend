package com.project.foodDelivery.controller;

import com.project.foodDelivery.dto.LoginRequest;
import com.project.foodDelivery.dto.RegisterRequest;
import com.project.foodDelivery.model.User;
import com.project.foodDelivery.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest req){
        try {
            User registeredUser = userService.registerUser(req);
            return ResponseEntity.created(URI.create("/api/users/" + registeredUser.getId())).body(registeredUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        String username = request.getEmail();
        String password = request.getPassword();
        System.out.println("Login attempt for user: " + username);
        System.out.println("Password provided: " + (password != null ? "****" : "null")); // Mask password in logs
        try {
            String token = userService.loginUser(username, password);
            return ResponseEntity.ok(Map.of("username", username, "token", token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
