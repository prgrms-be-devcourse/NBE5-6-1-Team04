package com.grepp.spring.app.controller.web.user;


import com.grepp.spring.app.controller.web.user.form.SignupResponse;
import com.grepp.spring.app.model.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/")
public class UserController {

    private final UserService userService;

    @GetMapping("users")
    public String getInfo(SignupResponse response){return "/api/users";}

    @GetMapping("signup")
    public String signupForm(Model model) {
        return "/api/signup";
    }

    @GetMapping("signin")
    public String signinForm(Model model) {
        return "/api/signin";
    }








}
