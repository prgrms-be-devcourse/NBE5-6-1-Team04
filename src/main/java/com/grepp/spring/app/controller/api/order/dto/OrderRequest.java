package com.grepp.spring.app.controller.api.order.dto;

import com.grepp.spring.app.model.order.dto.OrderItemDto;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private String userId;
    private String email;
    private List<OrderItemDto> orderItems;
    private int totalPrice;
    private String orderAddress;
}