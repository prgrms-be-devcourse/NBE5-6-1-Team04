package com.grepp.spring.app.model.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SigninFormDTO {
    @NotNull
    private String userId;
    @NotNull
    private String password;

    public SigninFormDTO() {}

    @Builder
    public SigninFormDTO(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
