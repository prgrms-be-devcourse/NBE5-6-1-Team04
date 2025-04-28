package com.grepp.spring.app.model.user.dto;

import com.grepp.spring.app.model.auth.Code.Role;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("GuestUser")
public class GuestUser {

    private String email;
    private Role role;
    private String address;
}