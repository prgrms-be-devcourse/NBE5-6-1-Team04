package com.grepp.spring.app.controller.api.order.dto;

import com.grepp.spring.app.model.order.dto.OrderDto;
import com.grepp.spring.app.model.order.dto.OrderItemDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDetailResponse {
    private OrderResponse order;
    private List<OrderItemResponse> orderItems;

    public OrderDetailResponse() {
        this.orderItems = new ArrayList<>();
    }

    public static OrderDetailResponse from(OrderDto orderDto, List<OrderItemDto> orderItemDtos) {
        OrderDetailResponse response = new OrderDetailResponse();

        OrderResponse orderResponse = OrderResponse.from(orderDto);
        response.setOrder(orderResponse);

        List<OrderItemResponse> itemResponses = orderItemDtos.stream()
                .map(OrderItemResponse::from)
                .collect(Collectors.toList());

        response.setOrderItems(itemResponses);

        return response;
    }
}