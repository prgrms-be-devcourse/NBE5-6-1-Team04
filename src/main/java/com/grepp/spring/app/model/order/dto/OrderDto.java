package com.grepp.spring.app.model.order.dto;

import com.grepp.spring.app.model.order.entity.Order;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderDto {
    @NotNull
    private Long orderId;
    private String userId;
    private String email;
    private int orderCount;
    private int totalPrice;
    private String orderStatus;
    private String orderAddress;
    private LocalDateTime createdAt;

    public static OrderDto from(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getOrderId());
        orderDto.setUserId(order.getUserId());
        orderDto.setOrderCount(order.getOrderCount());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setOrderStatus(order.getOrderStatus());
        orderDto.setOrderAddress(order.getOrderAddress());
        orderDto.setCreatedAt(order.getCreatedAt());

        if (order.getUserId() != null && order.getUserId().startsWith("guest_")) {
            orderDto.setUserId(null);
        }

        return orderDto;
    }

    public Order toEntity() {
        Order order = new Order();
        order.setOrderId(this.orderId);
        order.setUserId(this.userId);
        order.setOrderCount(this.orderCount);
        order.setTotalPrice(this.totalPrice);
        order.setOrderStatus(this.orderStatus);
        order.setOrderAddress(this.orderAddress);
        order.setCreatedAt(this.createdAt);
        return order;
    }

    public boolean isGuest() {
        return userId == null || userId.isEmpty() || userId.startsWith("guest_");
    }
}