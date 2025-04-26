package com.grepp.spring.app.controller.web.user.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GuestSigninRequest {
    @NotBlank
    @Email
    private String email;

}
