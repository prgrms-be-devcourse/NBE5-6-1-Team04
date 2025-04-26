package com.grepp.spring.app.model.order.dto;

import com.grepp.spring.app.model.order.entity.OrderItem;
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

    public static OrderItemDto from(OrderItem orderItem) {
        OrderItemDto dto = new OrderItemDto();
        dto.setOrderItemId(orderItem.getOrderItemId());
        dto.setOrderId(orderItem.getOrderId());
        dto.setProductId(orderItem.getProductId());
        dto.setOrderCount(orderItem.getOrderCount());
        return dto;
    }

    public OrderItem toEntity() {
        OrderItem entity = new OrderItem();
        entity.setOrderItemId(this.orderItemId);
        entity.setOrderId(this.orderId);
        entity.setProductId(this.productId);
        entity.setOrderCount(this.orderCount);
        return entity;
    }
}