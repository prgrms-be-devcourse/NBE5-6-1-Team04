package com.grepp.spring.app.model.order;

import com.grepp.spring.app.model.order.code.OrderStatus;
import com.grepp.spring.app.model.order.dto.DirectOrderDto;
import com.grepp.spring.app.model.order.dto.OrderDto;
import com.grepp.spring.app.model.order.dto.OrderItemDto;
import com.grepp.spring.app.model.order.entity.Order;
import com.grepp.spring.app.model.order.entity.OrderItem;
import com.grepp.spring.app.model.payment.PaymentService;
import com.grepp.spring.app.model.user.UserService;
import com.grepp.spring.app.model.user.dto.GuestUser;
import com.grepp.spring.app.model.user.dto.User;
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
    private final PaymentService paymentService;
    private final UserService userService;

    private static final Map<Long, String> guestOrderEmailMap = new ConcurrentHashMap<>();

    @Transactional
    public OrderDto createOrder(OrderDto orderDto, List<OrderItemDto> orderItems) {
        orderDto.setCreatedAt(LocalDateTime.now());
        orderDto.setOrderStatus(OrderStatus.ORDERED.name());

        if (orderDto.isGuest() && orderDto.getEmail() != null && !orderDto.getEmail().isEmpty()) {
            GuestUser guestUser = userService.GuestSignin(orderDto.getEmail());

            User user = userService.findByEmail(orderDto.getEmail());
            if (user != null) {
                orderDto.setUserId(user.getUserId());
            } else {
                throw new RuntimeException("비회원 사용자 생성에 실패했습니다.");
            }

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
            Long orderItemId = item.getOrderItemId();
            item.setOrderId(orderId);
            item.setOrderItemId(orderItemId);

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
        User user = userService.findByEmail(email);

        if (user == null) {
            return new ArrayList<>();
        }
        List<Order> orderEntities = orderRepository.getOrdersByUserId(user.getUserId());

        return orderEntities.stream()
                .map(order -> {
                    OrderDto dto = OrderDto.from(order);
                    dto.setEmail(email);
                    return dto;
                })
                .collect(Collectors.toList());
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

    public List<OrderDto> getOrdersByStatus(String status) {
        List<Order> orderEntities = orderRepository.getOrdersByStatus(status);
        return orderEntities.stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDto createDirectOrder(DirectOrderDto directOrderDto) {
        boolean isLoggedIn = directOrderDto.getUserId() != null && !directOrderDto.getUserId().isEmpty();

        User user = null;
        if (isLoggedIn) {
            user = userService.findById(directOrderDto.getUserId());
            if (user == null) {
                throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
            }
        }

        OrderDto orderDto = new OrderDto();

        if (isLoggedIn) {
            orderDto.setUserId(directOrderDto.getUserId());
            orderDto.setOrderAddress(directOrderDto.getAddress() != null && !directOrderDto.getAddress().isEmpty()
                    ? directOrderDto.getAddress() : user.getAddress());
        } else {
            if (directOrderDto.getEmail() == null || directOrderDto.getEmail().isEmpty() ||
                    directOrderDto.getAddress() == null || directOrderDto.getAddress().isEmpty()) {
                throw new RuntimeException("비회원 주문 시 이메일과 주소는 필수입니다.");
            }

            GuestUser guestUser = userService.GuestSignin(directOrderDto.getEmail());
            User createdUser = userService.findByEmail(directOrderDto.getEmail());
            if (createdUser != null) {
                orderDto.setUserId(createdUser.getUserId());
            } else {
                throw new RuntimeException("비회원 사용자 생성에 실패했습니다.");
            }

            orderDto.setEmail(directOrderDto.getEmail());
            orderDto.setOrderAddress(directOrderDto.getAddress());
        }

        orderDto.setTotalPrice(directOrderDto.getTotalPrice());
        orderDto.setOrderCount(directOrderDto.getProductCount());

        List<OrderItemDto> orderItems = new ArrayList<>();
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(directOrderDto.getProductId());
        orderItemDto.setOrderCount(directOrderDto.getProductCount());
        orderItems.add(orderItemDto);

        OrderDto createdOrder = createOrder(orderDto, orderItems);

        paymentService.processPayment(createdOrder.getOrderId(), directOrderDto.getTotalPrice());

        return createdOrder;
    }
}