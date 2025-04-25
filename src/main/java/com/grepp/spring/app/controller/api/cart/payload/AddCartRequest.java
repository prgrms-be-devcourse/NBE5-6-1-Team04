package com.grepp.spring.app.controller.api.cart.payload;

import lombok.Data;

@Data
public class AddCartRequest {
  private Long productId;
  private int productCount;
  private String userId;
}
