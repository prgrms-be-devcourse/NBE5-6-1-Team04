package com.grepp.spring.app.controller.web.user;


import com.grepp.spring.app.controller.web.user.form.SignupRequest;
import com.grepp.spring.app.model.auth.Code.Role;
import com.grepp.spring.app.model.user.UserService;
import com.grepp.spring.app.model.user.dto.Principal;
import com.grepp.spring.app.model.user.dto.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signupForm() {
        return "/api/signup";
    }
    @PostMapping("/signup")
    public String signup(@Valid @RequestBody SignupRequest request, BindingResult bindingResult,Model model) {
        if(bindingResult.hasErrors()){
            return "/signup";
        }
        model.addAttribute("signupRequest", request);

        userService.signup(request.toDto(), Role.ROLE_CUSTOMER);
        return "redirect:/";
    }


    @GetMapping("/signin")
    public String signinForm() {
        return "/api/signin";
    }

    @GetMapping("/guest-signin")
    public String gusetsigninForm() {
        return "/api/guest_signin";
    }

    @GetMapping("/users")
    public String getUserInfo( HttpSession session,Model model) {

        Principal principal = (Principal) session.getAttribute("principal");


        if (principal == null) {
            return "redirect:/signin";
        }

        User user = userService.findById(session.getId());

        model.addAttribute("user", user);
        return "users";
    }








}
