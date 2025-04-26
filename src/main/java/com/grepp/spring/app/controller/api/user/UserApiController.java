package com.grepp.spring.app.controller.api.user;


import com.grepp.spring.app.controller.web.user.form.GuestSigninRequest;
import com.grepp.spring.app.controller.web.user.form.SigninRequest;
import com.grepp.spring.app.model.auth.Code.Role;
import com.grepp.spring.app.model.user.UserService;
import com.grepp.spring.app.model.user.dto.GuestUser;
import com.grepp.spring.app.model.user.dto.Principal;
import com.grepp.spring.app.model.user.dto.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/signin")
    public ResponseEntity<Principal> signin(@Valid @ModelAttribute SigninRequest request,
        HttpSession session) {
        Principal principal = userService.signin(request.getUserId(), request.getPassword());

        session.setAttribute("principal", principal);

        return new ResponseEntity<>(principal, HttpStatus.CREATED);
    }


    @PostMapping("/guest-signin")
    public ResponseEntity<GuestUser> guestSignin(@Valid @RequestBody GuestSigninRequest request,
        HttpSession session) {
        GuestUser user = userService.GuestSignin(request.getEmail());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

      if (!(user.getRole() == Role.ROLE_GUEST)) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
      }

        session.setAttribute("GuestUser", user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
