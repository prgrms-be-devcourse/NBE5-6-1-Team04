package com.grepp.spring.app.controller.web.user;

import com.grepp.spring.app.model.user.dto.SigninFormDto;
import com.grepp.spring.app.model.user.dto.SignupFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/signin")
    public String signinPage(Model model) {
        model.addAttribute("signinForm", new SigninFormDto());
        return "user/signin";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("signupForm", new SignupFormDto());
        return "user/signup";
    }

}
