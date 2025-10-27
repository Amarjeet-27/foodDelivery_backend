package com.project.foodDelivery.repo;

import com.project.foodDelivery.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item, Long> {
}
