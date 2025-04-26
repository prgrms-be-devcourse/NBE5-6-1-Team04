package com.grepp.spring.app.controller.web.order;

import com.grepp.spring.app.model.order.OrderService;
import com.grepp.spring.app.model.order.dto.DirectOrderDto;
import com.grepp.spring.app.model.order.dto.OrderDto;
import com.grepp.spring.app.model.user.UserService;
import com.grepp.spring.app.model.user.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    @PostMapping("/orders/direct-order")
    public String showDirectOrderPage(
            @RequestParam("productId") Long productId,
            @RequestParam("productCount") int productCount,
            @RequestParam(value = "userId", required = false) String userId,
            Model model) {

        log.info("즉시 구매 페이지 요청: productId={}, count={}, userId={}", productId, productCount, userId);


        // TODO: Product 관련 모델 주입

        boolean isLoggedIn = userId != null && !userId.isEmpty();
        model.addAttribute("isLoggedIn", isLoggedIn);

        if (isLoggedIn) {
            User user = userService.findById(userId);
            model.addAttribute("user", user);
            model.addAttribute("userId", userId);
        }

        return "user/my-page";
    }

    @PostMapping("/orders/process-direct-order")
    public String processDirectOrder(
            @RequestParam("productId") Long productId,
            @RequestParam("productCount") int productCount,
            @RequestParam("totalPrice") int totalPrice,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "address", required = false) String address,
            RedirectAttributes redirectAttributes) {

        log.info("즉시 구매 처리 요청: productId={}, count={}, totalPrice={}, userId={}, email={}",
                productId, productCount, totalPrice, userId, email);

        try {
            DirectOrderDto directOrderDto = new DirectOrderDto();
            directOrderDto.setProductId(productId);
            directOrderDto.setProductCount(productCount);
            directOrderDto.setTotalPrice(totalPrice);
            directOrderDto.setUserId(userId);
            directOrderDto.setEmail(email);
            directOrderDto.setAddress(address);

            OrderDto createdOrder = orderService.createOrderDirectOrder(directOrderDto);

            redirectAttributes.addAttribute("orderId", createdOrder.getOrderId());
            return "user/my-page";

        } catch (Exception e) {
            log.error("즉시 구매 처리 중 오류 발생", e);
            redirectAttributes.addFlashAttribute("errorMessage", "주문 처리 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/";
        }
    }
}