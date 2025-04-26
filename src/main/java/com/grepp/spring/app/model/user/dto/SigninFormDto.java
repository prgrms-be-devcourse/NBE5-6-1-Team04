package com.grepp.spring.app.model.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SigninFormDto {
    @NotNull
    private String userId;
    @NotNull
    private String password;

    public SigninFormDto() {}

    @Builder
    public SigninFormDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
