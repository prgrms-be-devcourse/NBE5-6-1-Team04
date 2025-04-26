package com.grepp.spring.app.model.order.dto;

import lombok.Data;

@Data
public class DirectOrderDto {
    private String userId;
    private String email;
    private String address;
    private Long productId;
    private Integer productCount;
    private Integer totalPrice;
}