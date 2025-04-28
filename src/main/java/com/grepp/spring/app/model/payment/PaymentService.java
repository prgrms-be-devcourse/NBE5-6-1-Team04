package com.grepp.spring.app.model.payment;

import com.grepp.spring.app.model.order.OrderService;
import com.grepp.spring.app.model.order.code.OrderStatus;
import com.grepp.spring.app.model.order.dto.OrderDto;
import com.grepp.spring.app.model.payment.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    public PaymentService(
            PaymentRepository paymentRepository,
            @Lazy OrderService orderService
    ) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

    @Transactional
    public PaymentDto processPayment(Long orderId, int paymentPrice) {

        PaymentDto existingPayment = paymentRepository.getPaymentByOrderId(orderId);
        if (existingPayment != null) {
            throw new RuntimeException("이미 결제가 완료된 주문입니다. 주문 ID: " + orderId);
        }

        OrderDto order = orderService.getOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("주문 정보를 찾을 수 없습니다. 주문 ID: " + orderId);
        }

        if (order.getTotalPrice() != paymentPrice) {
            throw new RuntimeException("결제 금액이 주문 금액과 일치하지 않습니다. " + "주문 금액: " + order.getTotalPrice() + ", 결제 금액: " + paymentPrice);
        }

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setOrderId(orderId);
        paymentDto.setPaymentPrice(paymentPrice);
        paymentDto.setCreatedAt(LocalDateTime.now());

        paymentRepository.insertPayment(paymentDto);

        orderService.updateOrderStatus(orderId, OrderStatus.PAID);

        return paymentDto;
    }

    public PaymentDto getPaymentByOrderId(Long orderId) {
        return paymentRepository.getPaymentByOrderId(orderId);
    }
}