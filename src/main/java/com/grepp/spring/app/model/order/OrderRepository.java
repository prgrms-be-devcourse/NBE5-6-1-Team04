package com.grepp.spring.app.model.order;

import com.grepp.spring.app.model.order.dto.OrderDto;
import com.grepp.spring.app.model.order.dto.OrderItemDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderRepository {

    @Insert("INSERT INTO `order` (user_id, order_count, total_price, created_at, order_status, order_address) " +
            "VALUES (#{userId}, #{orderCount}, #{totalPrice}, #{createdAt}, #{orderStatus}, #{orderAddress})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId", keyColumn = "order_id")
    void insertOrder(OrderDto orderDto);

    @Insert("INSERT INTO order_item (order_id, product_id, order_count) " +
            "VALUES (#{orderId}, #{productId}, #{orderCount})")
    @Options(useGeneratedKeys = true, keyProperty = "orderItemId", keyColumn = "order_item_id")
    void insertOrderItem(OrderItemDto orderItemDto);

    @Select("SELECT order_id AS orderId, user_id AS userId, order_count AS orderCount, " +
            "total_price AS totalPrice, created_at AS createdAt, order_status AS orderStatus, " +
            "order_address AS orderAddress FROM `order` WHERE order_id = #{orderId}")
    OrderDto getOrderById(Long orderId);

    @Select("SELECT order_id AS orderId, user_id AS userId, order_count AS orderCount, " +
            "total_price AS totalPrice, created_at AS createdAt, order_status AS orderStatus, " +
            "order_address AS orderAddress FROM `order` WHERE user_id = #{userId}")
    List<OrderDto> getOrdersByUserId(String userId);

    // 비회원 주문 조회 - user_id 컬럼에 이메일 형식으로 저장된 값을 조회
    @Select("SELECT order_id AS orderId, user_id AS userId, order_count AS orderCount, " +
            "total_price AS totalPrice, created_at AS createdAt, order_status AS orderStatus, " +
            "order_address AS orderAddress FROM `order` WHERE user_id LIKE CONCAT('guest:', #{email})")
    List<OrderDto> getOrdersByEmail(String email);

    @Select("SELECT order_item_id AS orderItemId, order_id AS orderId, product_id AS productId, " +
            "order_count AS orderCount FROM order_item WHERE order_id = #{orderId}")
    List<OrderItemDto> getOrderItemsByOrderId(Long orderId);

    @Update("UPDATE `order` SET order_status = #{orderStatus} WHERE order_id = #{orderId}")
    void updateOrderStatus(@Param("orderId") Long orderId, @Param("orderStatus") String orderStatus);

    @Select("SELECT order_id AS orderId, user_id AS userId, order_count AS orderCount, " +
            "total_price AS totalPrice, created_at AS createdAt, order_status AS orderStatus, " +
            "order_address AS orderAddress FROM `order` WHERE order_status = #{status}")
    List<OrderDto> getOrdersByStatus(String status);

    @Select("SELECT order_id AS orderId, user_id AS userId, order_count AS orderCount, " +
            "total_price AS totalPrice, created_at AS createdAt, order_status AS orderStatus, " +
            "order_address AS orderAddress FROM `order`")
    List<OrderDto> getAllOrders();
}