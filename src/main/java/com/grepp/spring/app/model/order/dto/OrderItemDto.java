package com.grepp.spring.app.model.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDto {
    @NotNull
    private Long orderItemId;
    @NotNull
    private Long orderId;
    @NotNull
    private Long productId;
    private int orderCount;
    private String productName;
    private int productPrice;
}