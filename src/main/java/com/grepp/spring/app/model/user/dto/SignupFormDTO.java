package com.grepp.spring.app.model.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SignupFormDTO {
    @NotNull
    private String userId;
    @NotNull
    private String password;
    private String name;
    private String address;
    private String email;

    public SignupFormDTO() {}

    @Builder
    public SignupFormDTO(String userId, String password, String name, String address,
        String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.address = address;
        this.email = email;
    }
}
