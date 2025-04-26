package com.grepp.spring.app.model.order;

import com.grepp.spring.app.model.order.code.OrderStatus;
import com.grepp.spring.app.model.order.dto.OrderDto;
import com.grepp.spring.app.model.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@RestController
public class OrderScheduler {

    private final OrderService orderService;
    private final PaymentService paymentService;

    @Scheduled(cron = "0 0 14 * * ?")
    public void scheduledProcessOrderedOrders() {
        processOrderedOrders();
    }

    // 테스트용 엔드포인트
    @GetMapping("/test/process-orders")
    public String testProcessOrders() {
        processOrderedOrders();
        return "주문 처리가 완료되었습니다.";
    }

    // 테스트용 엔드포인트
    @GetMapping("/test/process-ship")
    public String testProcessShipping() {
        processShipping();
        return "주문 배송 처리가 완료되었습니다.";
    }

    public void processOrderedOrders() {
        log.info("결제 완료 주문 일괄 처리 시작");

        List<OrderDto> orders = orderService.getOrdersByStatus(OrderStatus.PAID.name());
        log.info("처리할 결제 완료 주문 수: {}", orders.size());

        for (OrderDto order : orders) {
            try {
                orderService.updateOrderStatus(order.getOrderId(), OrderStatus.PROCESSING);
                log.info("주문 상태 업데이트 완료: 주문번호 {}, 상태 PAID -> PROCESSING", order.getOrderId());
            } catch (Exception e) {
                log.error("주문 상태 업데이트 실패: 주문번호 {}", order.getOrderId(), e);
            }
        }

        log.info("결제 완료 주문 일괄 처리 완료");
    }

    @Scheduled(cron = "0 0 17 * * ?")
    public void processShipping() {
        log.info("처리중 주문 배송 처리 시작");

        List<OrderDto> orders = orderService.getOrdersByStatus(OrderStatus.PROCESSING.name());
        log.info("처리할 배송 주문 수: {}", orders.size());

        for (OrderDto order : orders) {
            try {
                orderService.updateOrderStatus(order.getOrderId(), OrderStatus.SHIPPED);
                log.info("주문 배송 처리 완료: 주문번호 {}, 상태 PROCESSING -> SHIPPED", order.getOrderId());
            } catch (Exception e) {
                log.error("주문 배송 처리 중 오류 발생: 주문번호 {}", order.getOrderId(), e);
            }
        }

        log.info("처리중 주문 배송 처리 완료");
    }
}