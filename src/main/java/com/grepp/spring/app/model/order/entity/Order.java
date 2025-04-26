package com.grepp.spring.app.model.order.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Alias("Order")
public class Order {
    private Long orderId;
    private String userId;
    private int orderCount;
    private int totalPrice;
    private String orderStatus;
    private String orderAddress;
    private LocalDateTime createdAt;
}