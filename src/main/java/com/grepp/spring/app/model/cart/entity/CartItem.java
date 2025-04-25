package com.grepp.spring.app.model.cart.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;



@Data
@Alias("CartItem")
public class CartItem {
  private Long orderItemId;
  private Long cartId;
  private Long productId;
  private int productCount;

}
