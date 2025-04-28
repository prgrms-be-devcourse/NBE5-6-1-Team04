package com.grepp.spring.app.controller.api.order.dto;

import com.grepp.spring.app.model.order.code.OrderStatus;
import lombok.Data;

@Data
public class OrderStatusUpdateRequest {
    private Long orderId;
    private OrderStatus status;
}