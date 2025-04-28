package com.grepp.spring.app.controller.web.user;


import com.grepp.spring.app.model.user.UserService;
import com.grepp.spring.app.model.user.dto.Principal;
import com.grepp.spring.app.model.user.dto.SigninFormDto;
import com.grepp.spring.app.model.user.dto.SignupFormDto;
import com.grepp.spring.app.model.user.dto.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("signupForm", new SignupFormDto());
        return "user/signup";
    }
    @GetMapping("/user/signin")
    public String signinForm(Model model) {
        model.addAttribute("signupForm", new SignupFormDto());
        return "user/signin";

    }

//    @GetMapping("/signin")
//    public String signinPage(Model model) {
//        model.addAttribute("signinForm", new SigninFormDto());
//        return "user/signin";
//    }

    @GetMapping("/guest-signin")
    public String gusetsigninForm(Model model) {
        model.addAttribute("guestSigninForm", new SigninFormDto());
        return "user/guest-signin";
    }

    @GetMapping("/users")
    public String getUserInfo(HttpSession session, Model model) {

        Principal principal = (Principal) session.getAttribute("principal");

        if (principal == null) {
            return "redirect:/signin";
        }

        User user = userService.findById(session.getId());

        model.addAttribute("user", user);
        return "users";
    }

}
