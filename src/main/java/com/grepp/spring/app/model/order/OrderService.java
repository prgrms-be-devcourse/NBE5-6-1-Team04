package com.grepp.spring.app.model.order;

import com.grepp.spring.app.model.order.code.OrderStatus;
import com.grepp.spring.app.model.order.dto.OrderDto;
import com.grepp.spring.app.model.order.dto.OrderItemDto;
import com.grepp.spring.app.model.order.entity.Order;
import com.grepp.spring.app.model.order.entity.OrderItem;
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
        orderDto.setCreatedAt(LocalDateTime.now());
        orderDto.setOrderStatus(OrderStatus.ORDERED.name());

        if (orderDto.isGuest() && orderDto.getEmail() != null && !orderDto.getEmail().isEmpty()) {
            orderDto.setUserId(null);

            Order orderEntity = orderDto.toEntity();
            orderRepository.insertOrder(orderEntity);

            Long orderId = orderEntity.getOrderId();
            guestOrderEmailMap.put(orderId, orderDto.getEmail());

            orderDto.setOrderId(orderId);
        } else {
            Order orderEntity = orderDto.toEntity();
            orderRepository.insertOrder(orderEntity);
            orderDto.setOrderId(orderEntity.getOrderId());
        }

        Long orderId = orderDto.getOrderId();
        if (orderId == null) {
            throw new RuntimeException("주문 ID 생성 실패");
        }

        for (OrderItemDto item : orderItems) {
            item.setOrderId(orderId);
            item.setOrderItemId(generateOrderItemId());

            OrderItem orderItemEntity = item.toEntity();
            orderRepository.insertOrderItem(orderItemEntity);
        }

        return orderDto;
    }

    public OrderDto getOrderById(Long orderId) {
        Order orderEntity = orderRepository.getOrderById(orderId);
        if (orderEntity == null) {
            return null;
        }

        OrderDto orderDto = OrderDto.from(orderEntity);

        if (orderDto.isGuest()) {
            String email = guestOrderEmailMap.get(orderId);
            if (email != null) {
                orderDto.setEmail(email);
            }
        }

        return orderDto;
    }

    public List<OrderDto> getOrdersByUserId(String userId) {
        List<Order> orderEntities = orderRepository.getOrdersByUserId(userId);
        return orderEntities.stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getOrdersByEmail(String email) {
        List<Long> orderIds = guestOrderEmailMap.entrySet().stream()
                .filter(entry -> email.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<OrderDto> orders = new ArrayList<>();
        for (Long orderId : orderIds) {
            Order order = orderRepository.getOrderById(orderId);
            if (order != null) {
                OrderDto dto = OrderDto.from(order);
                dto.setEmail(email);
                orders.add(dto);
            }
        }

        return orders;
    }

    public List<OrderDto> getAllOrders() {
        List<Order> orderEntities = orderRepository.getAllOrders();
        List<OrderDto> orderDtos = orderEntities.stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());

        for (OrderDto dto : orderDtos) {
            if (dto.getUserId() == null) {
                String email = guestOrderEmailMap.get(dto.getOrderId());
                if (email != null) {
                    dto.setEmail(email);
                }
            }
        }

        return orderDtos;
    }

    public List<OrderItemDto> getOrderItemsByOrderId(Long orderId) {
        return orderRepository.getOrderItemsByOrderId(orderId);
    }

    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus status) {
        orderRepository.updateOrderStatus(orderId, status.name());
    }

    public List<OrderDto> getOrderedOrders() {
        List<Order> orderEntities = orderRepository.getOrdersByStatus(OrderStatus.ORDERED.name());
        return orderEntities.stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    }

    private Long generateOrderItemId() {
        return System.currentTimeMillis() + (long) (Math.random() * 100);
    }

    public List<OrderDto> getOrdersByStatus(String status) {
        List<Order> orderEntities = orderRepository.getOrdersByStatus(status);
        return orderEntities.stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    }
}