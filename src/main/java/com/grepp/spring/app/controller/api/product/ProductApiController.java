package com.grepp.spring.app.controller.api.product;

import com.grepp.spring.app.controller.web.product.form.ProductDto;
import com.grepp.spring.app.model.order.ImageUtil;
import com.grepp.spring.app.model.order.ProductService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductApiController {

  private final ProductService orderService;


  //상품리스트 가져오기
  @GetMapping("/products")
  public List<ProductDto> productlist(Model model){
    List<ProductDto> productDto = orderService.selectListAll();
    return productDto;
  }

  //상세정보
  @GetMapping("/products/{id}")
  public ProductDto productDetailInfo(@PathVariable("id") int id,Model model){
    ProductDto productDetail = orderService.selectProductDetail(id);
    return productDetail;
  }

  //상품정보 추가
  @PostMapping("/new-products")
  public ResponseEntity<?> insertproduct(@RequestParam(required = false) String productName,
      @RequestParam (required = false) int price, @RequestParam (required = false) String description,
      @RequestParam (required = false) int stock, @RequestParam(required = false) MultipartFile imageFile)
      throws IOException {

    String base64 = ImageUtil.encodeBase64(imageFile);

    orderService.insertproducts(new ProductDto(productName,price,description,stock,base64));
    return ResponseEntity.ok().build();
  }
  //상품 정보 삭제
//  @DeleteMapping("/products/{id}")
//  public ResponseEntity<?> deleteproduct(@PathVariable ("id") int id){
//    orderService.deleteproduct(id);
//    return ResponseEntity.ok().build();
//
//  }

  //상품 정보 수정
//  @PutMapping("/products")
//  public ResponseEntity<?> updateproduct(){
//
//
//  }








}
