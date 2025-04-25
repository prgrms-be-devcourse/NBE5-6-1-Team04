package com.grepp.spring.app.controller.web.product.form;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {

  private int productId;
  private String productName;
  private int price;
  private String description;
  private int stock;
  private LocalDateTime createdAt;
  private String imageBase64;

  public ProductDto(String productName, int price, String description
     , int stock,String imageBase64) {
    this.productName = productName;
    this.price = price;
    this.description = description;
    this.stock = stock;
    this.imageBase64 = imageBase64;
    this.createdAt = LocalDateTime.now();
  }
}

