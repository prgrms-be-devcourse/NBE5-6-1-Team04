package com.grepp.spring.app.model.order.code;

public enum OrderStatus {
    PENDING("대기중"),
    PAID("결제완료"),
    PROCESSING("처리중"),
    SHIPPED("배송중"),
    DELIVERED("배송완료"),
    CANCELED("취소됨");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name();
    }
}