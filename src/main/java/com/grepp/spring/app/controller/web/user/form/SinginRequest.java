package com.grepp.spring.app.controller.web.user.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SinginRequest {

    @NotBlank
    private String userId;

    @NotBlank
    @Size(min=4, max= 20)
    private String password;

}
