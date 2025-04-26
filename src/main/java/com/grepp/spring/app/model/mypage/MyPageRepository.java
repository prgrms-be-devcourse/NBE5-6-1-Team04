package com.grepp.spring.app.model.mypage;

import com.grepp.spring.app.model.mypage.dto.MyPageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface MyPageRepository {

  @Select("SELECT " +
      "order_id AS orderId, " +
      "total_price AS totalPrice, " +
      "order_status AS orderStatus, " +
      "order_address AS orderAddress " +
      "FROM `order` " +
      "WHERE user_id = #{userId}")
  List<MyPageDto> findOrdersByUserId(@Param("userId") String userId);
}
