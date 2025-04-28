package com.grepp.spring.app.model.user.dto;

import com.grepp.spring.app.model.auth.Code.Role;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("LoginUser")
public class LoginUser {

    private String userId;
    private String password;
    Role role;

}
