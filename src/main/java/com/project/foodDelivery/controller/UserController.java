package com.project.foodDelivery.controller;

import com.project.foodDelivery.dto.LoginRequest;
import com.project.foodDelivery.dto.RegisterRequest;
import com.project.foodDelivery.model.User;
import com.project.foodDelivery.repo.UserRepo;
import com.project.foodDelivery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;





    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userService.getUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }




}
