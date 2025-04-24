package com.grepp.spring.app.controller.api.order;

import com.grepp.spring.app.controller.api.order.dto.OrderDetailResponse;
import com.grepp.spring.app.controller.api.order.dto.OrderRequest;
import com.grepp.spring.app.controller.api.order.dto.OrderStatusUpdateRequest;
import com.grepp.spring.app.model.order.OrderService;
import com.grepp.spring.app.model.order.dto.OrderDto;
import com.grepp.spring.app.model.order.dto.OrderItemDto;
import com.grepp.spring.infra.response.ApiResponse;
import com.grepp.spring.infra.response.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<OrderDto>> createOrder(
            @RequestBody @Valid OrderRequest request) {

        OrderDto orderDto = new OrderDto();

        if (request.getUserId() != null && !request.getUserId().isEmpty()) {
            orderDto.setUserId(request.getUserId());
            orderDto.setEmail(null);
        } else if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            orderDto.setUserId(null);
            orderDto.setEmail(request.getEmail());
        } else {
            return new ResponseEntity<>(ApiResponse.error(ResponseCode.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

        orderDto.setOrderCount(request.getOrderItems().size());
        orderDto.setTotalPrice(request.getTotalPrice());
        orderDto.setOrderAddress(request.getOrderAddress());

        OrderDto createdOrder = orderService.createOrder(orderDto, request.getOrderItems());

        if (createdOrder.isGuest() && request.getEmail() != null) {
            createdOrder.setEmail(request.getEmail());
        }

        return new ResponseEntity<>(ApiResponse.success(createdOrder), HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<?>> getMyOrders(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String email) {

        if (userId != null && !userId.isEmpty()) {
            List<OrderDto> orders = orderService.getOrdersByUserId(userId);
            return new ResponseEntity<>(ApiResponse.success(orders), HttpStatus.OK);
        } else if (email != null && !email.isEmpty()) {
            List<OrderDto> orders = orderService.getOrdersByEmail(email);

            for (OrderDto order : orders) {
                if (order.getUserId() != null && order.getUserId().startsWith("guest:")) {
                    String extractedEmail = order.getUserId().substring(6);
                    order.setEmail(extractedEmail);
                    order.setUserId(null);
                }
            }

            return new ResponseEntity<>(ApiResponse.success(orders), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ApiResponse.error(ResponseCode.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> getOrderDetail(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrderById(orderId);

        if (order == null) {
            return new ResponseEntity<>(ApiResponse.error(ResponseCode.NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        if (order.getUserId() != null && order.getUserId().startsWith("guest:")) {
            String email = order.getUserId().substring(6);
            order.setEmail(email);
            order.setUserId(null);
        }

        List<OrderItemDto> orderItems = orderService.getOrderItemsByOrderId(orderId);

        OrderDetailResponse orderDetail = new OrderDetailResponse();
        orderDetail.setOrder(order);
        orderDetail.setOrderItems(orderItems);

        return new ResponseEntity<>(ApiResponse.success(orderDetail), HttpStatus.OK);
    }

    @GetMapping("/orders/admin")
    public ResponseEntity<ApiResponse<List<OrderDto>>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();

        for (OrderDto order : orders) {
            if (order.getUserId() != null && order.getUserId().startsWith("guest:")) {
                String email = order.getUserId().substring(6);
                order.setEmail(email);
            }
        }

        return new ResponseEntity<>(ApiResponse.success(orders), HttpStatus.OK);
    }

    @PutMapping("/orders/admin")
    public ResponseEntity<ApiResponse<Void>> updateOrderStatus(
            @RequestBody @Valid OrderStatusUpdateRequest request) {

        orderService.updateOrderStatus(request.getOrderId(), request.getStatus());

        return new ResponseEntity<>(ApiResponse.noContent(), HttpStatus.OK);
    }
}