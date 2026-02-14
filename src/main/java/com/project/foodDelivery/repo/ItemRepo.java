package com.project.foodDelivery.repo;

import com.project.foodDelivery.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Long> {
    List<Item> findByUser_Email(String email);
}
