package com.grepp.spring.app.model.mypage;

import com.grepp.spring.app.model.mypage.dto.MyPageOrderDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface MyPageOrderRepository {
  @Select("SELECT " +
      "order_id AS orderId, " +
      "total_price AS totalPrice, " +
      "order_status AS orderStatus, " +
      "order_address AS orderAddress " +
      "FROM `order` " +
      "WHERE user_id = #{userId}")
  List<MyPageOrderDto> findOrdersByUserId(@Param("userId") String userId);
}
