package com.grepp.spring.app.controller.api.order.dto;

import com.grepp.spring.app.model.order.dto.DirectOrderDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DirectOrderRequest {
    private String userId;
    private String email;
    private String address;
    @NotNull
    private Long productId;
    @NotNull
    @Min(value = 1, message = "상품 수량은 1개 이상이어야 합니다.")
    private Integer productCount;
    @NotNull(message = "결제 금액은 필수입니다.")
    @Min(value = 0, message = "결제 금액은 0 이상이어야 합니다.")
    private Integer totalPrice;

    public DirectOrderDto toDto() {
        DirectOrderDto dto = new DirectOrderDto();
        dto.setUserId(this.userId);
        dto.setEmail(this.email);
        dto.setAddress(this.address);
        dto.setProductId(this.productId);
        dto.setProductCount(this.productCount);
        dto.setTotalPrice(this.totalPrice);
        return dto;
    }
}