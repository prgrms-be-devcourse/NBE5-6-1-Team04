package com.grepp.spring.app.model.cart.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

// 사용자에게 보여줄 데이터 전달용 DTO
// MyBatis 매핑용 or 내부 로직에서 사용하는 중간 DTO

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
  private int totalPrice;  // 각 상품의 총 가격 (수량 * 가격)
}
