package com.grepp.spring.app.controller.api.order.dto;

import com.grepp.spring.app.model.order.dto.OrderDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponse {
    private Long orderId;
    private String userId;
    private String email;
    private int orderCount;
    private int totalPrice;
    private String orderStatus;
    private String orderAddress;
    private LocalDateTime createdAt;

    public static OrderResponse from(OrderDto orderDto) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(orderDto.getOrderId());
        response.setUserId(orderDto.getUserId());
        response.setEmail(orderDto.getEmail());
        response.setOrderCount(orderDto.getOrderCount());
        response.setTotalPrice(orderDto.getTotalPrice());
        response.setOrderStatus(orderDto.getOrderStatus());
        response.setOrderAddress(orderDto.getOrderAddress());
        response.setCreatedAt(orderDto.getCreatedAt());
        return response;
    }
}