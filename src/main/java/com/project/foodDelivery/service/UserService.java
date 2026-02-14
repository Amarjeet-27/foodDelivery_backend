package com.project.foodDelivery.service;

import com.project.foodDelivery.dto.RegisterRequest;
import com.project.foodDelivery.model.User;
import com.project.foodDelivery.repo.UserRepo;
import com.project.foodDelivery.security.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.OptionalInt;


@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    public UserService(PasswordEncoder passwordEncoder, JwtUtils jwtUtils, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.userRepo = userRepo;
    }


    public User registerUser(RegisterRequest req){
        Optional<User> existingUser = userRepo.findByEmail(req.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with email " + req.getEmail() + " already exists.");
        } else {
            String encodedPassword = passwordEncoder.encode(req.getPassword());
            User newUser = new User();
            newUser.setName(req.getName());
            newUser.setEmail(req.getEmail());
            newUser.setPassword(encodedPassword);
            newUser.setRole(req.getRole());
            return userRepo.save(newUser);
        }
    }
    public String loginUser(String email, String password){
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password!"));

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new IllegalArgumentException("Invalid username or password!");
        }
        return jwtUtils.generateJwtToken(email,user.getRole());

    }

    public Optional<User> getUserByEmail(String email){
        return userRepo.findByEmail(email);
    }

    public Optional<User> getUserById(Long id){
        return userRepo.findById(id);
    }

}
