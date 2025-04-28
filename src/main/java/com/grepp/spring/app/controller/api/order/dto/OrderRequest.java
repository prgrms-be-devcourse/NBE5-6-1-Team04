package com.grepp.spring.app.controller.api.order.dto;

import com.grepp.spring.app.model.order.dto.OrderDto;
import com.grepp.spring.app.model.order.dto.OrderItemDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRequest {
    private String userId;
    private String email;
    @NotEmpty
    private List<OrderItemDto> orderItems;
    @NotNull
    private Integer totalPrice;
    @NotNull
    private String orderAddress;

    public OrderDto toOrderDto() {
        OrderDto orderDto = new OrderDto();

        if (userId != null && !userId.isEmpty()) {
            orderDto.setUserId(userId);
        } else if (email != null && !email.isEmpty()) {
            orderDto.setEmail(email);
        }

        orderDto.setOrderCount(orderItems.size());
        orderDto.setTotalPrice(totalPrice);
        orderDto.setOrderAddress(orderAddress);
        orderDto.setCreatedAt(LocalDateTime.now());

        return orderDto;
    }
}