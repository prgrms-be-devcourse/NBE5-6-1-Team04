package com.grepp.spring.app.model.user.dto;

import com.grepp.spring.app.model.auth.Code.Role;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.type.Alias;

@Alias("USER_PRINCIPAL")
public record Principal (
    List<Role> roles,
    LocalDateTime createdAt

){

    public static final Principal CUSTOMER = new Principal(
        List.of(Role.ROLE_CUSTOMER),
        LocalDateTime.now());

    public static final Principal GUEST = new Principal(
        List.of(Role.ROLE_GUEST),
        LocalDateTime.now());


}
