package com.grepp.spring.app.model.cart.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("CartItemDto")
public class CartItemDto {
  private Long id;
  private Long cartId;
  private Long productId;
  private String productName;
  private int productCount;
  private int productPrice;
  private String productImageUrl;
  private int totalPrice;
}
