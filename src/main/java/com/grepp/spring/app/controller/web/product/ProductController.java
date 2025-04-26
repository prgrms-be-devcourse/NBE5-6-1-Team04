package com.grepp.spring.app.controller.web.product;

import com.grepp.spring.app.model.product.dto.ProductDto;
import com.grepp.spring.app.model.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product/detail")
    public String productDetails(@RequestParam("id") int id, Model model) {
        ProductDto productDto = productService.selectProductDetail(id);
        model.addAttribute("product", productDto);
        return "product/product-detail";
    }

}
