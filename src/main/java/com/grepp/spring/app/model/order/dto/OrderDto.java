package com.grepp.spring.app.model.order.dto;

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

    public boolean isGuest() {
        return userId == null || userId.isEmpty();
    }
}