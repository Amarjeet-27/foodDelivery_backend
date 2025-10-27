package com.project.foodDelivery.service;

import com.project.foodDelivery.model.Item;
import com.project.foodDelivery.model.User;
import com.project.foodDelivery.repo.ItemRepo;
import com.project.foodDelivery.repo.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepo itemRepo;
    private final UserRepo userRepo;

    public ItemService(ItemRepo itemRepo,UserRepo userRepo) {
        this.itemRepo = itemRepo;
        this.userRepo= userRepo;
    }

    public List<Item> getAllItems() {
        return itemRepo.findAll();
    }

    public Optional<Item>getItemById(Long id) {
        return itemRepo.findById(id);
    }
    public Item saveItem(Item item) {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email= auth.getName();

        User user= userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("user not found"));

        item.setUser(user);
        return itemRepo.save(item);
    }
    public void deleteItem(Long id) {
        itemRepo.deleteById(id);
    }




}
