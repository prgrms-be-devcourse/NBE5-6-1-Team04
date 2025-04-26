package com.grepp.spring.app.controller.api.order;

import com.grepp.spring.app.controller.api.order.dto.OrderDetailResponse;
import com.grepp.spring.app.controller.api.order.dto.OrderRequest;
import com.grepp.spring.app.controller.api.order.dto.OrderResponse;
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
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @RequestBody @Valid OrderRequest request) {

        OrderDto orderDto = request.toOrderDto();

        if ((orderDto.getUserId() == null || orderDto.getUserId().isEmpty())
                && (orderDto.getEmail() == null || orderDto.getEmail().isEmpty())) {
            return new ResponseEntity<>(ApiResponse.error(ResponseCode.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

        OrderDto createdOrder = orderService.createOrder(orderDto, request.getOrderItems());
        OrderResponse response = OrderResponse.from(createdOrder);

        return new ResponseEntity<>(ApiResponse.success(response), HttpStatus.CREATED);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> getOrderDetail(@PathVariable Long orderId) {
        OrderDto orderDto = orderService.getOrderById(orderId);

        if (orderDto == null) {
            return new ResponseEntity<>(ApiResponse.error(ResponseCode.NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        List<OrderItemDto> orderItemDtos = orderService.getOrderItemsByOrderId(orderId);
        OrderDetailResponse response = OrderDetailResponse.from(orderDto, orderItemDtos);

        return new ResponseEntity<>(ApiResponse.success(response), HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<?>> getMyOrders(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String email) {

        if (userId != null && !userId.isEmpty()) {
            List<OrderDto> orderDtos = orderService.getOrdersByUserId(userId);

            List<OrderResponse> responses = orderDtos.stream()
                    .map(OrderResponse::from)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(ApiResponse.success(responses), HttpStatus.OK);

        } else if (email != null && !email.isEmpty()) {
            List<OrderDto> orderDtos = orderService.getOrdersByEmail(email);

            List<OrderResponse> responses = orderDtos.stream()
                    .map(OrderResponse::from)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(ApiResponse.success(responses), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(ApiResponse.error(ResponseCode.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/orders/admin")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        List<OrderDto> orderDtos = orderService.getAllOrders();

        List<OrderResponse> responses = orderDtos.stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());

        return new ResponseEntity<>(ApiResponse.success(responses), HttpStatus.OK);
    }

    @PutMapping("/orders/admin")
    public ResponseEntity<ApiResponse<Void>> updateOrderStatus(
            @RequestBody @Valid OrderStatusUpdateRequest request) {

        orderService.updateOrderStatus(request.getOrderId(), request.getStatus());

        return new ResponseEntity<>(ApiResponse.noContent(), HttpStatus.OK);
    }
}