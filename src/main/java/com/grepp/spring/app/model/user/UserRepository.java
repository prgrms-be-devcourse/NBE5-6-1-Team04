package com.grepp.spring.app.model.user;


import com.grepp.spring.app.model.user.dto.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRepository {

    @Select("select count(*) from user where user_id = #{userId}")
    boolean existsUserId(String userId);

    @Insert("insert into user (USER_ID, PASSWORD, NAME, ADDRESS, EMAIL,ROLE,CREATED_AT) "
        + "values(#{userId}, #{password},#{name} ,#{address}, #{email}, #{role},#{createdAt})")
    void insert(User dto);
}
