package com.grepp.spring.app.controller.web.admin;

import com.grepp.spring.app.model.order.OrderService;
import com.grepp.spring.app.model.order.dto.OrderDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminPageController {

    private final OrderService orderService;

    @GetMapping("/admin")
    public String showAllOrders(Model model) {
        List<OrderDto> Orders = orderService.getAllOrders();
        model.addAttribute("orders", Orders);
        return "admin/admin-page";
    }

}
