package com.grepp.spring.app.controller.web.user.form;

import com.grepp.spring.app.model.user.dto.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank
    private String userId;
    @NotBlank
    @Size(min = 4, max = 10)
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @Email
    private String email;


    public User toDto(){
        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        user.setEmail(email);
        user.setName(name);
        user.setAddress(address);
        return user;
    }

}
