package com.grepp.spring.app.controller.web.cart;

import com.grepp.spring.app.model.cart.dto.CartResponse;
import com.grepp.spring.app.model.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    @GetMapping("/cart")
    public String getCart(@RequestParam(name = "userId") String userId, Model model) {
        log.info("장바구니 페이지 요청: userId={}", userId);
        CartResponse cartResponse = cartService.getCart(userId);
        model.addAttribute("cart", cartResponse);
        return "cart/cart";
    }
} 