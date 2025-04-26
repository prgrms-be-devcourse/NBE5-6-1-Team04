package com.grepp.spring.app.model.payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PaymentDto {
    private Long paymentId;
    @NotNull
    private Long orderId;
    private int paymentPrice;
    private LocalDateTime createdAt;
}