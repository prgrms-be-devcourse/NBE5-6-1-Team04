package com.grepp.spring.app.controller.api.order.dto;

import com.grepp.spring.app.model.order.dto.OrderDto;
import com.grepp.spring.app.model.order.dto.OrderItemDto;
import lombok.Data;

import java.util.List;

@Data
public class OrderDetailResponse {
    private OrderDto order;
    private List<OrderItemDto> orderItems;
}