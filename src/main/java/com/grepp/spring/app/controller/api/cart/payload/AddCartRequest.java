package com.grepp.spring.app.controller.api.cart.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddCartRequest {
  @NotNull(message = "상품 ID는 필수입니다.")
  private Long productId;
  
  @Min(value = 1, message = "상품 수량은 1개 이상이어야 합니다.")
  private int productCount;
  
  @NotBlank(message = "사용자 ID는 필수입니다.")
  private String userId;
}
