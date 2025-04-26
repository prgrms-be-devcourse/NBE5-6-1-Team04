package com.grepp.spring.app.controller.api.user.dto;

import com.grepp.spring.app.model.user.dto.LoginUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SigninRequest {

    @NotBlank
    private String userId;

    @NotBlank
    @Size(min=4, max= 100)
    private String password;

    public LoginUser toDto(){
        LoginUser user = new LoginUser();
        user.setUserId(userId);
        user.setPassword(password);
        return user;
    }


}
