package com.grepp.spring.app.model.order;

import com.grepp.spring.app.model.order.dto.OrderItemDto;
import com.grepp.spring.app.model.order.entity.Order;
import com.grepp.spring.app.model.order.entity.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderRepository {

    @Insert("INSERT INTO `order` (user_id, order_count, total_price, created_at, order_status, order_address) " +
            "VALUES (#{userId}, #{orderCount}, #{totalPrice}, #{createdAt}, #{orderStatus}, #{orderAddress})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId", keyColumn = "order_id")
    void insertOrder(Order order);

    @Insert("INSERT INTO order_item (order_id, product_id, order_count) " +
            "VALUES (#{orderId}, #{productId}, #{orderCount})")
    @Options(useGeneratedKeys = true, keyProperty = "orderItemId", keyColumn = "order_item_id")
    void insertOrderItem(OrderItem orderItem);

    @Select("SELECT order_id AS orderId, user_id AS userId, order_count AS orderCount, " +
            "total_price AS totalPrice, created_at AS createdAt, order_status AS orderStatus, " +
            "order_address AS orderAddress FROM `order` WHERE order_id = #{orderId}")
    Order getOrderById(Long orderId);

    @Select("SELECT order_id AS orderId, user_id AS userId, order_count AS orderCount, " +
            "total_price AS totalPrice, created_at AS createdAt, order_status AS orderStatus, " +
            "order_address AS orderAddress FROM `order` WHERE user_id = #{userId}")
    List<Order> getOrdersByUserId(String userId);

    @Select("SELECT o.order_id AS orderId, o.user_id AS userId, o.order_count AS orderCount, " +
            "o.total_price AS totalPrice, o.created_at AS createdAt, o.order_status AS orderStatus, " +
            "o.order_address AS orderAddress FROM `order` o " +
            "JOIN user u ON o.user_id = u.user_id " +
            "WHERE u.email = #{email}")
    List<Order> getOrdersByEmail(String email);

    @Select("SELECT oi.order_item_id AS orderItemId, oi.order_id AS orderId, oi.product_id AS productId, " +
            "oi.order_count AS orderCount, p.product_name AS productName, p.price AS productPrice " +
            "FROM order_item oi " +
            "JOIN product p ON oi.product_id = p.product_id " +
            "WHERE oi.order_id = #{orderId}")
    List<OrderItemDto> getOrderItemsByOrderId(Long orderId);

    @Select("SELECT order_item_id AS orderItemId, order_id AS orderId, product_id AS productId, " +
            "order_count AS orderCount FROM order_item WHERE order_id = #{orderId}")
    List<OrderItem> getOrderItemEntitiesByOrderId(Long orderId);

    @Update("UPDATE `order` SET order_status = #{orderStatus} WHERE order_id = #{orderId}")
    void updateOrderStatus(@Param("orderId") Long orderId, @Param("orderStatus") String orderStatus);

    @Select("SELECT order_id AS orderId, user_id AS userId, order_count AS orderCount, " +
            "total_price AS totalPrice, created_at AS createdAt, order_status AS orderStatus, " +
            "order_address AS orderAddress FROM `order` WHERE order_status = #{status}")
    List<Order> getOrdersByStatus(String status);

    @Select("SELECT order_id AS orderId, user_id AS userId, order_count AS orderCount, " +
            "total_price AS totalPrice, created_at AS createdAt, order_status AS orderStatus, " +
            "order_address AS orderAddress FROM `order`")
    List<Order> getAllOrders();
}