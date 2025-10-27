package com.project.foodDelivery.service;

import com.project.foodDelivery.model.Item;
import com.project.foodDelivery.model.Order;
import com.project.foodDelivery.model.OrderItem;
import com.project.foodDelivery.model.OrderStatus;
import com.project.foodDelivery.repo.ItemRepo;
import com.project.foodDelivery.repo.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final ItemRepo itemRepo;

    public OrderService(OrderRepo orderRepo, ItemRepo itemRepo) {
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
    }

    @Transactional
    public Order placeOrder(Order order) {
        // Business logic to place an order
        BigDecimal total = BigDecimal.ZERO;

        for(OrderItem oi: order.getItems()){
            Item item = itemRepo.findById(oi.getItem().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Item not found: " + oi.getItem().getId()));
            if(item.getStock()<oi.getQuantity()){
                throw new IllegalArgumentException("Insufficient stock for item: " + item.getName());
            }
            item.setStock(item.getStock()-oi.getQuantity());
            itemRepo.save(item);
            oi.setPriceAtOrder(item.getPrice());
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(oi.getQuantity())));
        }
        order.setStatus(OrderStatus.PENDING);
        return orderRepo.save(order);
    }

    public Order getOrder(Long id){
        return orderRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Order not found: "+id));
    }

    public List<Order> getAllOrders(String email){
        return orderRepo.findByCustomerEmail(email);
    }
}
