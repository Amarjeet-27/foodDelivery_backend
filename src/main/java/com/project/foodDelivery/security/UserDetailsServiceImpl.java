package com.project.foodDelivery.security;

import com.project.foodDelivery.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private  final UserRepo userRepo;
    public UserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @Override
    public UserDetailsImpl loadUserByUsername(String email) {
        return userRepo.findByEmail(email)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }



}
