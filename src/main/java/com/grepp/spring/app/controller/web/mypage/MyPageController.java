package com.grepp.spring.app.controller.web.mypage;

import com.grepp.spring.app.model.mypage.MyPageService;
import com.grepp.spring.app.model.mypage.dto.MyPageOrderDto;
import com.grepp.spring.app.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;
    private final UserRepository userRepository;

    @GetMapping("/my-page/{id}")
    public String showMyOrders(@PathVariable String id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
         // 현재 인증된 사용자 정보 가져오기
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

         // 인증된 사용자의 ID 가져오기
         String authenticatedUserId = authentication.getName();

         // 요청된 ID와 인증된 사용자의 ID가 일치하는지 확인
         if (!id.equals(authenticatedUserId)) {
             redirectAttributes.addFlashAttribute("errorMessage", "접근 권한이 없습니다.");
             return "redirect:/";
         }

         // DB에 사용자가 존재하는지 확인
         if (!userRepository.existsUserId(id)) {
             redirectAttributes.addFlashAttribute("errorMessage", "존재하지 않는 사용자입니다.");
             return "redirect:/";
         }
        
        List<MyPageOrderDto> myOrders = myPageService.getMyOrders(id, id);
        model.addAttribute("orders", myOrders);
        return "user/my-page";
    }
}
