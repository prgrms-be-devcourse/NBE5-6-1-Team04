package com.grepp.spring.app.model.cart.dto;


import lombok.Data;


@Data
public class CartItemResponse {
  private Long cartItemId;
  private Long productId;
  private String productName;
  private int productPrice;
  private int productCount;
  private String thumbnailUrl;

  public static CartItemResponse from(CartItemDto dto) {
    CartItemResponse response = new CartItemResponse();
    response.setProductId(dto.getProductId());
    response.setProductName(dto.getProductName());
    response.setProductPrice(dto.getPrice());
    response.setProductCount(dto.getProductCount());
    response.setThumbnailUrl(dto.getThumbnailUrl());
    return response;
  }
}
