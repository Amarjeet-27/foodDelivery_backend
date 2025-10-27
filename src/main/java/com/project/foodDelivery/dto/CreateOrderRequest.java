package com.project.foodDelivery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateOrderRequest {
    private String customerEmail;
    private String customerAddress;
    private String customerPhone;
    private List<ItemOrder> items;

    @Data
    @NoArgsConstructor
    public static class ItemOrder{
        public Long itemId;
        public Integer quantity;
    }
}
