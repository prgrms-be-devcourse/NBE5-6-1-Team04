package com.grepp.spring.app.controller.web.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class productDetailController {

    @GetMapping("product/detail")
    public String showProductDetails(@RequestParam("id") String id, Model model) {
        // TODO: product 상품 상세 정보 가져오기
        return "product/product-detail";
    }

}
