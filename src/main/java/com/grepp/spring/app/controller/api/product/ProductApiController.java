package com.grepp.spring.app.controller.api.product;

import com.grepp.spring.app.model.product.dto.ProductDto;
import com.grepp.spring.app.model.order.ImageUtil;
import com.grepp.spring.app.model.product.service.ProductService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductApiController {

  private final ProductService productService;


  @GetMapping("/products")
  public ResponseEntity<List<ProductDto>> getProducts() {
    List<ProductDto> responseDtoList = productService.selectListAll();
    return ResponseEntity.ok(responseDtoList);
  }



  @GetMapping("/products/{id}")
  public ResponseEntity<ProductDto> getProductDetail(@PathVariable int id) {
    ProductDto productDto = productService.selectProductDetail(id);
    if (productDto.getImageBase64() == null
        || productDto.getImageBase64().trim().isEmpty()
        || "null".equalsIgnoreCase(productDto.getImageBase64().trim()))
    {
        productDto.setImageBase64(null);
    }
    return ResponseEntity.ok(productDto);
  }

  //상품정보 추가
  @PostMapping("/new-products")
  public ResponseEntity<?> insertproduct(@RequestParam(required = false) String productName,
      @RequestParam (required = false) int price, @RequestParam (required = false) String description,
      @RequestParam (required = false) int stock, @RequestParam(required = false) MultipartFile imageFile)
      throws IOException {

    String base64 = ImageUtil.encodeBase64(imageFile);

    productService.insertproducts(new ProductDto(productName,price,description,stock,base64));
    return ResponseEntity.ok().build();
  }
  @PreAuthorize("hasRole('ADMIN')")
//  상품 정보 삭제
  @DeleteMapping("/products/{id}")
  public ResponseEntity<?> deleteproduct(@PathVariable ("id") int id){
    productService.deleteproduct(id);
    return ResponseEntity.ok().build();

  }

  //상품 정보 수정
//  @PutMapping("/products")
//  public ResponseEntity<?> updateproduct(){
//
//
//  }

}
