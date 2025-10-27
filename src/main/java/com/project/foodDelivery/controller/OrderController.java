package com.project.foodDelivery.controller;

import com.project.foodDelivery.dto.CreateOrderRequest;
import com.project.foodDelivery.model.Item;
import com.project.foodDelivery.model.Order;
import com.project.foodDelivery.model.OrderItem;
import com.project.foodDelivery.repo.ItemRepo;
import com.project.foodDelivery.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins="*")
public class OrderController {
    private final OrderService orderService;
    private final ItemRepo itemRepo;

    public OrderController(OrderService orderService, ItemRepo itemRepo) {
        this.orderService = orderService;
        this.itemRepo = itemRepo;
    }


    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
//        System.out.println(email);
        List<Order> orders = orderService.getAllOrders(email);
//        System.out.println(orders);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest req){
        try {
            Order order = new Order();
            order.setCustomerEmail(req.getCustomerEmail());
            order.setCustomerAddress(req.getCustomerAddress());
            order.setCustomerPhone(req.getCustomerPhone());
            BigDecimal value = BigDecimal.ZERO;
            for(CreateOrderRequest.ItemOrder itemOrder : req.getItems()){
                Item item=itemRepo.findById(itemOrder.itemId).orElseThrow(()->new IllegalArgumentException("Item not found: "+itemOrder.itemId));
                OrderItem oi = new OrderItem();
                oi.setItem(item);
                oi.setQuantity(itemOrder.getQuantity());
                oi.setPriceAtOrder(item.getPrice());
                BigDecimal total = item.getPrice().multiply(BigDecimal.valueOf(itemOrder.getQuantity()));
                value = value.add(total);
                order.addItem(oi);
            }
            order.setTotalAmount(value);
            Order savedOrder = orderService.placeOrder(order);
            return ResponseEntity.created(URI.create("/api/orders/"+savedOrder.getId())).body(savedOrder);
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id){
        Order order = orderService.getOrder(id);
        if(order==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }
}
