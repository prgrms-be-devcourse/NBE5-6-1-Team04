package com.grepp.spring.app.model.cart.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Cart {
  @NotNull(message = "장바구니 ID는 필수입니다.")
  private Long cartId;
  
  @NotBlank(message = "사용자 ID는 필수입니다.")
  private String userId;
}