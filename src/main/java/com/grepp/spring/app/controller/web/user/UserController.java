package com.grepp.spring.app.controller.web.user;

import com.grepp.spring.app.controller.web.user.form.SignupRequest;
import com.grepp.spring.app.controller.web.user.form.SignupResponse;
import com.grepp.spring.app.model.auth.Code.Role;
import com.grepp.spring.app.model.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
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
    public String getUserInfo(SignupResponse response) {
        return "users";
    }

    @GetMapping("signup")
    public String signupForm(Model model) {
        return "signup";
    }

    @PostMapping("signup")
    public String signup(@RequestBody SignupRequest request, Model model) {
        if (ObjectUtils.isEmpty(request.getUserId())) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        log.info("request:{}", request);

        model.addAttribute("signupRequest", request);
        userService.signup(request.toDto(), Role.ROLE_USER);
        return "redirect:/";
    }


}
