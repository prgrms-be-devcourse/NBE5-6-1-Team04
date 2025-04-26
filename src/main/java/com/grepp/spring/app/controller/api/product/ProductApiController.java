package com.grepp.spring.app.controller.api.product;

import com.grepp.spring.app.controller.web.product.form.ProductDto;
import com.grepp.spring.app.model.order.ImageUtil;
import com.grepp.spring.app.model.order.ProductService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping("/api")
@Controller
@RequiredArgsConstructor

public class ProductApiController {

  private final ProductService orderService;


  @GetMapping("/products")
  public String productlist(
      @RequestParam(value = "page", defaultValue = "1") int pageNum,
      @RequestParam(value = "size", defaultValue = "5") int productsPerPage,
      Model model
  ) {
    List<ProductDto> productDtoList = orderService.selectListAll();

    int startIndex = (pageNum - 1) * productsPerPage;
    int endIndex = Math.min(startIndex + productsPerPage, productDtoList.size());


    model.addAttribute("productList", productDtoList);
    model.addAttribute("productsPerPage", productsPerPage);
    model.addAttribute("pageNum", pageNum);
    model.addAttribute("startIndex", startIndex);
    model.addAttribute("endIndex", endIndex);

    return "index";
  }


  //상세정보
  @GetMapping("/products/{id}")
  public String productDetailInfo(@PathVariable("id") int id,Model model){
    ProductDto productDetail = orderService.selectProductDetail(id);

    if (productDetail.getImageBase64() == null
        || productDetail.getImageBase64().trim().isEmpty()
        || "null".equalsIgnoreCase(productDetail.getImageBase64().trim())) {
      productDetail.setImageBase64(null); // 확실히 비우기
    }
    model.addAttribute("product",productDetail);
    return "product/product-detail";
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
