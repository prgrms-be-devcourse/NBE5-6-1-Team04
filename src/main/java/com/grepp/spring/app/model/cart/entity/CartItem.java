package com.grepp.spring.app.model.cart.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("CartItem")
public class CartItem {
  private Long cartItemId;
  
  @NotNull(message = "장바구니 ID는 필수입니다.")
  private Long cartId;
  
  @NotNull(message = "상품 ID는 필수입니다.")
  private Long productId;
  
  @Min(value = 1, message = "상품 수량은 1개 이상이어야 합니다.")
  private int productCount;
  
  private String productName;
  private int productPrice;
  private String productImageUrl;
}
