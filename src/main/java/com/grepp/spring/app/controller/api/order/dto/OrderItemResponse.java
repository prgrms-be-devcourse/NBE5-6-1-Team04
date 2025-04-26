package com.grepp.spring.app.controller.api.order.dto;

import com.grepp.spring.app.model.order.dto.OrderItemDto;
import lombok.Data;

@Data
public class OrderItemResponse {
    private Long orderItemId;
    private Long productId;
    private String productName;
    private int productPrice;
    private int orderCount;
    private int totalItemPrice;

    public static OrderItemResponse from(OrderItemDto orderItemDto) {
        OrderItemResponse response = new OrderItemResponse();
        response.setOrderItemId(orderItemDto.getOrderItemId());
        response.setProductId(orderItemDto.getProductId());
        response.setProductName(orderItemDto.getProductName());
        response.setProductPrice(orderItemDto.getProductPrice());
        response.setOrderCount(orderItemDto.getOrderCount());
        response.setTotalItemPrice(orderItemDto.getProductPrice() * orderItemDto.getOrderCount());
        return response;
    }
}