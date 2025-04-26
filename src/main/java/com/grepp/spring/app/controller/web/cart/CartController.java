package com.grepp.spring.app.controller.web.cart;

import com.grepp.spring.app.model.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    @GetMapping("/cart")
    public String getCart() {
        return "cart/cart";
    }
} 