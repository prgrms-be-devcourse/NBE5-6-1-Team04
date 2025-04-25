package com.grepp.spring.app.model.cart.entity.dto;

import lombok.Data;

@Data
public class CartItemResponse {
  private Long cartItemId;
  private Long productId;
  private String productName;
  private int productPrice;
  private int productCount;
  private String productImageUrl;
  private int totalPrice;

  public static CartItemResponse from(CartItemDto dto) {
    CartItemResponse response = new CartItemResponse();
    response.setCartItemId(dto.getId());
    response.setProductId(dto.getProductId());
    response.setProductName(dto.getProductName());
    response.setProductPrice(dto.getProductPrice());
    response.setProductCount(dto.getProductCount());
    response.setProductImageUrl(dto.getProductImageUrl());
    response.setTotalPrice(dto.getProductPrice() * dto.getProductCount());
    return response;
  }
}
