package com.grepp.spring.app.model.user.dto;

import com.grepp.spring.app.model.auth.Code.Role;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.type.Alias;

@Alias("USER_PRINCIPAL")
public record Principal (
    String userId,
    List<Role> roles,
    LocalDateTime createdAt

){

    public static final Principal CUSTOMER = new Principal(
        "customer",
        List.of(Role.ROLE_CUSTOMER),
        LocalDateTime.now());

}
