package com.grepp.spring.app.controller.web.cart;

//장바구니 조회해서 JSP에 넘김

import com.grepp.spring.app.model.cart.service.CartService;
import com.grepp.spring.app.model.cart.dto.CartResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/view")  // JSP 뷰를 위한 /view 경로
public class CartController {
    private final CartService cartService;

    @GetMapping("/cart")  // /view/cart로 접근
    public String getCart(@RequestParam(name = "userId") String userId, Model model) {
        log.info("장바구니 페이지 요청: userId={}", userId);
        CartResponse cartResponse = cartService.getCart(userId);
        model.addAttribute("cart", cartResponse);
        return "cart/cart";  // cart.jsp를 찾아 렌더링
    }
} 