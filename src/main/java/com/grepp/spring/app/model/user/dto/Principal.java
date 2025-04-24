package com.grepp.spring.app.model.user.dto;

import com.grepp.spring.app.model.auth.Code.Role;
import java.time.LocalDateTime;
import java.util.List;

public record Principal (
    String userId,
    List<Role> Roles

){

}
