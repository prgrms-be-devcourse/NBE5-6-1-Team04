package com.grepp.spring.app.controller.web.user.form;

import java.text.DateFormat;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserDto {

  private String userId;
  private String password;
  private String address;
  private String email;
  private String role;
  private String name;
  private LocalDateTime createdAt;


}
