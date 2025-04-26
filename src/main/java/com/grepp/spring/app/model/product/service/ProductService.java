package com.grepp.spring.app.model.product.service;

import com.grepp.spring.app.model.product.dto.ProductDto;
import com.grepp.spring.app.model.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepositoryService;


  public List<ProductDto> selectListAll() {

    return productRepositoryService.selectlistall();
  }

  public ProductDto selectProductDetail(int id) {

    return productRepositoryService.selectProductDetail(id);
  }

  @Transactional
  public void insertproducts(ProductDto productDto) {

    productRepositoryService.insertproduct(productDto);
  }

  @Transactional
  public void deleteproduct(int id) {


    productRepositoryService.deleteproduct(id);
  }
}
