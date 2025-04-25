package com.grepp.spring.app.controller.api.user;


import com.grepp.spring.app.controller.web.user.form.SigninRequest;
import com.grepp.spring.app.controller.web.user.form.SignupRequest;
import com.grepp.spring.app.model.auth.Code.Role;
import com.grepp.spring.app.model.user.UserService;
import com.grepp.spring.app.model.user.dto.Principal;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

  private final UserService userService;


  @PostMapping("signup")
  public String signup(@RequestBody SignupRequest request,
      Model model) {
    if (request.getUserId() == null) {
      throw new IllegalArgumentException("User ID cannot be null");
    }


    model.addAttribute("signupRequest", request);

    userService.signup(request.toDto(), Role.ROLE_CUSTOMER);
    return "redirect:/";
  }
  @PostMapping("signin")
  public String signin(@RequestBody SigninRequest request, HttpSession session){
    Principal principal = userService.signin(request.getUserId(),request.getPassword());

    session.setAttribute("principal",principal);

    return "redirect:/";
  }

}
