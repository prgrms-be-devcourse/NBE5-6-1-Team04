package com.grepp.spring.app.controller.api.cart;

import com.grepp.spring.app.controller.api.cart.payload.AddCartRequest;
import com.grepp.spring.app.controller.api.cart.payload.UpdateCartRequest;
import com.grepp.spring.app.model.cart.service.CartService;
import com.grepp.spring.app.model.cart.dto.CartResponse;
import com.grepp.spring.infra.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartApiController {
  private final CartService cartService;

  @GetMapping("/cart")
  public ResponseEntity<ApiResponse<CartResponse>> getCart(@RequestParam("userId") String userId) {
    log.info("장바구니 조회 요청: userId={}", userId);
    CartResponse cartResponse = cartService.getCart(userId);
    return ResponseEntity.ok(ApiResponse.success(cartResponse));
  }

  @PostMapping("/cart")
  public ResponseEntity<ApiResponse<String>> addToCart(@Valid @RequestBody AddCartRequest request) {
    log.info("장바구니 추가 요청: {}", request);
    cartService.add(request);
    return ResponseEntity.ok(ApiResponse.success("장바구니에 추가되었습니다."));
  }

  @PutMapping("/cart")
  public ResponseEntity<ApiResponse<String>> updateCartItemCount(@Valid @RequestBody UpdateCartRequest request) {
    log.info("장바구니 수량 수정 요청: {}", request);
    cartService.updateCount(request);
    return ResponseEntity.ok(ApiResponse.success("장바구니 수량이 수정되었습니다."));
  }

  @DeleteMapping("/cart/{cartItemId}")
  public ResponseEntity<ApiResponse<String>> deleteCartItem(@PathVariable("cartItemId") Long cartItemId) {
    log.info("장바구니 항목 삭제 요청: cartItemId={}", cartItemId);
    cartService.deleteItem(cartItemId);
    return ResponseEntity.ok(ApiResponse.success("장바구니 항목이 삭제되었습니다."));
  }

  @DeleteMapping("/cart/clear")
  public ResponseEntity<ApiResponse<String>> clearCart(@RequestParam("userId") String userId) {
    log.info("장바구니 비우기 요청: userId={}", userId);
    cartService.clearCart(userId);
    return ResponseEntity.ok(ApiResponse.success("장바구니가 비워졌습니다."));
  }
}
