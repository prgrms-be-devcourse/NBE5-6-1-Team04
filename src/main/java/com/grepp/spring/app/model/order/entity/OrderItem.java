package com.grepp.spring.app.model.order.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@Alias("OrderItem")
public class OrderItem {
    private Long orderItemId;
    private Long orderId;
    private Long productId;
    private int orderCount;
}