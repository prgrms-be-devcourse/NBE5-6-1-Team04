package com.grepp.spring.app.controller.api.cart.payload;

import lombok.Data;

@Data
public class UpdateCartRequest {
    private Long cartItemId;
    private int productCount;
    private String userId;
} 