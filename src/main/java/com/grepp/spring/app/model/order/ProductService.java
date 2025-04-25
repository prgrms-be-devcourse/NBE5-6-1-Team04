package com.grepp.spring.app.model.order;

import com.grepp.spring.app.controller.web.product.form.ProductDto;
import com.grepp.spring.app.model.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository orderRepository;


  public List<ProductDto> selectListAll() {

    return orderRepository.selectlistall();
  }

  public ProductDto selectProductDetail(int id) {

    return orderRepository.selectProductDetail(id);
  }

  @Transactional
  public void insertproducts(ProductDto productDto) {

    orderRepository.insertproduct(productDto);
  }

  @Transactional
  public void deleteproduct(int id) {


    orderRepository.deleteproduct(id);
  }
}
