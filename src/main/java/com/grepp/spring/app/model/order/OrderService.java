package com.grepp.spring.app.model.order;

import com.grepp.spring.app.model.order.code.OrderStatus;
import com.grepp.spring.app.model.order.dto.OrderDto;
import com.grepp.spring.app.model.order.dto.OrderItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private static final Map<Long, String> guestOrderEmailMap = new ConcurrentHashMap<>();

    @Transactional
    public OrderDto createOrder(OrderDto orderDto, List<OrderItemDto> orderItems) {
        Long orderId = generateOrderId();
        orderDto.setOrderId(orderId);
        orderDto.setCreatedAt(LocalDateTime.now());
        orderDto.setOrderStatus(OrderStatus.PENDING.name());

        if (orderDto.isGuest() && orderDto.getEmail() != null && !orderDto.getEmail().isEmpty()) {
            orderDto.setUserId(null);
            guestOrderEmailMap.put(orderId, orderDto.getEmail());
        }

        orderRepository.insertOrder(orderDto);

        for (OrderItemDto item : orderItems) {
            item.setOrderId(orderId);
            item.setOrderItemId(generateOrderItemId());
            orderRepository.insertOrderItem(item);
        }

        return orderDto;
    }

    public OrderDto getOrderById(Long orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public List<OrderDto> getOrdersByUserId(String userId) {
        return orderRepository.getOrdersByUserId(userId);
    }

    public List<OrderDto> getOrdersByEmail(String email) {
        List<Long> orderIds = guestOrderEmailMap.entrySet().stream()
                .filter(entry -> email.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<OrderDto> orders = new ArrayList<>();
        for (Long orderId : orderIds) {
            OrderDto order = orderRepository.getOrderById(orderId);
            if (order != null) {
                order.setEmail(email);
                orders.add(order);
            }
        }

        return orders;
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public List<OrderItemDto> getOrderItemsByOrderId(Long orderId) {
        return orderRepository.getOrderItemsByOrderId(orderId);
    }

    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus status) {
        orderRepository.updateOrderStatus(orderId, status.name());
    }

    public List<OrderDto> getPendingOrders() {
        return orderRepository.getOrdersByStatus(OrderStatus.PENDING.name());
    }

    private Long generateOrderId() {
        return System.currentTimeMillis();
    }

    private Long generateOrderItemId() {
        return System.currentTimeMillis() + (long) (Math.random() * 100);
    }
}