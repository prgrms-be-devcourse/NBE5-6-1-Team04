package com.grepp.spring.app.model.user.dto;

import com.grepp.spring.app.model.auth.Code.Role;
import java.time.LocalDateTime;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("User")
public class User {

    private String userId;
    private String password;
    private String name;
    private String address;
    private String email;
    private Role role;
    private LocalDateTime CreatedAt;



}
