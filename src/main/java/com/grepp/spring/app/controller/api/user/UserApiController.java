package com.grepp.spring.app.controller.api.user;


import com.grepp.spring.app.controller.api.user.dto.GuestSigninRequest;
import com.grepp.spring.app.controller.api.user.dto.SigninRequest;
import com.grepp.spring.app.controller.api.user.dto.SignupRequest;
import com.grepp.spring.app.model.auth.Code.Role;
import com.grepp.spring.app.model.user.UserService;
import com.grepp.spring.app.model.user.dto.GuestUser;
import com.grepp.spring.app.model.user.dto.Principal;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(
        @Valid @RequestBody SignupRequest request,
        BindingResult bindingResult,
        Model model) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("signupRequest", request);
        userService.signup(request.toDto(), Role.ROLE_CUSTOMER);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
//
//    @PostMapping("/signin")
//    public ResponseEntity<Principal> signin(
//        @Valid @RequestBody SigninRequest request, HttpSession session) {
//        Principal principal = userService.signin(request.getUserId(), request.getPassword());
//
//        session.setAttribute("principal", principal);
//        session.setAttribute("userId", request.getUserId());
//
//        return new ResponseEntity<>(principal, HttpStatus.CREATED);
//    }

    @PostMapping("/guest-signin")
    public ResponseEntity<Principal> guestSignin(
        @Valid @RequestBody GuestSigninRequest request, HttpSession session) {
        GuestUser user = userService.GuestSignin(request.getEmail());

        Principal principal = new Principal(List.of(Role.ROLE_GUEST), LocalDateTime.now());

        session.setAttribute("principal", principal);
        session.setAttribute("userId", user.getEmail());

        return new ResponseEntity<>(principal, HttpStatus.CREATED);
    }
}