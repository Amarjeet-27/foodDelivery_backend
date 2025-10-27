package com.project.foodDelivery.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerEmail;
    private String customerAddress;
    private String customerPhone;

    private LocalDateTime createdAt=LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private OrderStatus status=OrderStatus.PENDING;
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference  // to prevent infinite recursion -> serialisation
    private List<OrderItem> items = new ArrayList<>();
    public void addItem(OrderItem item) {
        item.setOrder(this);
        items.add(item);
    }
}
