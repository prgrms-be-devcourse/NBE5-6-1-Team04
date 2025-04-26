package com.grepp.spring.app.controller.web.mypage;

import com.grepp.spring.app.model.mypage.MyPageService;
import com.grepp.spring.app.model.mypage.dto.MyPageOrderDto;
import com.grepp.spring.app.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;
    private final UserRepository userRepository;

    @GetMapping("/my-page/{id}")
    public String showMyOrders(@PathVariable String id, Model model, HttpSession session) {
        String sessionUserId = (String) session.getAttribute("userId");
        List<MyPageOrderDto> myOrders = myPageService.getMyOrders(id, sessionUserId);
        model.addAttribute("orders", myOrders);
        return "user/my-page";
    }
}
