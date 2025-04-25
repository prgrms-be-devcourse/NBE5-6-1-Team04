package com.grepp.spring.app.model;

import com.grepp.spring.app.controller.web.user.form.UserDto;

import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper

public interface AuthRepository {

  @Select("select * from user where user_id = #{username}")
  Optional<UserDto> selectuserId(String username);

}

