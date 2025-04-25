package com.grepp.spring.app.controller.web.mypage;

import com.grepp.spring.app.model.order.OrderService;
import com.grepp.spring.app.model.order.dto.OrderDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final OrderService orderService;

    @GetMapping("/my-page/{id}")
    public String showMyOrders(@PathVariable String id, Model model) {
        List<OrderDto> myOrders = orderService.getOrdersByUserId(id);
        model.addAttribute("orders", myOrders);
        System.out.println("myOrders : " +myOrders.toString());
        return "user/my-page";
    }
}
