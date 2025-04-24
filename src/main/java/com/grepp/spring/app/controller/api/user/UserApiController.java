package com.grepp.spring.app.controller.api.user;


import com.grepp.spring.app.model.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;


}
