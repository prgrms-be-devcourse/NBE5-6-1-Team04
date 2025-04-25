package com.grepp.spring.app.controller.web.user;

import com.grepp.spring.app.model.user.dto.SigninFormDTO;
import com.grepp.spring.app.model.user.dto.SignupFormDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/signin")
    public String signinPage(Model model) {
        model.addAttribute("signinForm", new SigninFormDTO());
        return "user/signin";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("signupForm", new SignupFormDTO());
        return "user/signup";
    }

}
