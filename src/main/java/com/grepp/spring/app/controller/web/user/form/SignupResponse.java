package com.grepp.spring.app.controller.web.user.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupResponse {

    @NotBlank
    private String userId;
    @NotBlank
    @Size(min = 4, max = 10)
    private String password;
    @NotBlank
    @Email
    private String email;
}
