package com.grepp.spring.app.model.mypage.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyPageDto {

    @NotNull
    private Long orderId;
    private int totalPrice;
    private String orderStatus;
    private String orderAddress;
}
