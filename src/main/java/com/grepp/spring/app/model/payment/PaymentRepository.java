package com.grepp.spring.app.model.payment;

import com.grepp.spring.app.model.payment.dto.PaymentDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PaymentRepository {

    @Insert("INSERT INTO payment (order_id, payment_price, create_at) " +
            "VALUES (#{orderId}, #{paymentPrice}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "paymentId", keyColumn = "payment_id")
    void insertPayment(PaymentDto paymentDto);

    @Select("SELECT payment_id AS paymentId, order_id AS orderId, " +
            "payment_price AS paymentPrice, create_at AS createdAt " +
            "FROM payment WHERE order_id = #{orderId}")
    PaymentDto getPaymentByOrderId(Long orderId);
}