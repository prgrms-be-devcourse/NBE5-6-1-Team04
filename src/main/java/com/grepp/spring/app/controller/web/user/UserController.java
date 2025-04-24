package com.grepp.spring.app.controller.web.user;

import com.grepp.spring.app.controller.web.user.form.SignupRequest;
import com.grepp.spring.app.controller.web.user.form.SignupResponse;
import com.grepp.spring.app.controller.web.user.form.SinginRequest;
import com.grepp.spring.app.model.auth.Code.Role;
import com.grepp.spring.app.model.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("signup")
    public String signup(@RequestBody SignupRequest request,
        Model model) {
        if (request.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }


        model.addAttribute("signupRequest", request);

        userService.signup(request.toDto(), Role.ROLE_USER);
        return "redirect:/";
    }
    @PostMapping("signin")
    public String signin(SinginRequest request){
        return "redirect:/";
    }






}
