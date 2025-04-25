package com.grepp.spring.app.model.cart.dto;


import java.util.List;
import lombok.Data;

@Data
public class CartResponse {
  private Long cartId;
  private List<CartItemResponse> items;
  private int totalPrice;
}