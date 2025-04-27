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

    @PostMapping("/signin")
    public ResponseEntity<Principal> signin(
        @Valid @ModelAttribute SigninRequest request,
        HttpSession session) {
        Principal principal = userService.signin(request.getUserId(), request.getPassword());

        session.setAttribute("principal", principal);

        return new ResponseEntity<>(principal, HttpStatus.CREATED);
    }

    @PostMapping("/guest-signin")
    public ResponseEntity<GuestUser> guestSignin(
        @Valid @RequestBody GuestSigninRequest request) {
        GuestUser user = userService.GuestSignin(request.getEmail());

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
