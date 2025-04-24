package com.grepp.spring.app.model.user;



import com.grepp.spring.app.model.user.dto.LoginUser;
import com.grepp.spring.app.model.user.dto.User;
import java.util.Optional;
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




    @Select("select * from user where user_id = #{userId}")
    Optional<User> selectById(String userId);

    @Select("select user_id, password from user where user_id = #{userId}")
    Optional<LoginUser> selectLoginUserById(String userId);

}
