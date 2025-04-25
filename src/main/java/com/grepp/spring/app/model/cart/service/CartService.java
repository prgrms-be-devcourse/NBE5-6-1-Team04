package com.grepp.spring.app.model.cart.service;

import com.grepp.spring.app.controller.api.cart.payload.AddCartRequest;
import com.grepp.spring.app.controller.api.cart.payload.UpdateCartRequest;
import com.grepp.spring.app.model.cart.dto.CartItemDto;
import com.grepp.spring.app.model.cart.dto.CartItemResponse;
import com.grepp.spring.app.model.cart.dto.CartResponse;
import com.grepp.spring.app.model.cart.entity.CartItem;
import com.grepp.spring.app.model.cart.repository.CartRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

  private final CartRepository cartRepository;

  @Transactional
  public void add(@Valid AddCartRequest request) {
    try {
      Long cartId = cartRepository.findCartIdByUserId(request.getUserId());
      if (cartId == null) {
        throw new IllegalArgumentException("해당 사용자의 장바구니를 찾을 수 없습니다: " + request.getUserId());
      }
      
      CartItem cartItem = new CartItem();
      cartItem.setOrderItemId(System.currentTimeMillis()); // 임시로 현재 시간을 ID로 사용
      cartItem.setCartId(cartId);
      cartItem.setProductId(request.getProductId());
      cartItem.setProductCount(request.getProductCount());
      
      cartRepository.insertCartItem(cartItem);
      log.info("장바구니에 상품 추가 완료: cartId={}, productId={}", cartId, request.getProductId());
    } catch (Exception e) {
      log.error("장바구니 추가 중 오류 발생", e);
      throw e;
    }
  }

  public CartResponse getCart(String userId) {
    try {
      List<CartItemDto> cartItems = cartRepository.findCartItemsByUserId(userId);
      CartResponse response = new CartResponse();
      response.setItems(cartItems.stream()
              .map(CartItemResponse::from)
              .toList());
      response.setTotalPrice(cartItems.stream()
              .mapToInt(item -> item.getPrice() * item.getProductCount())
              .sum());
      return response;
    } catch (Exception e) {
      log.error("장바구니 조회 중 오류 발생: userId={}", userId, e);
      throw e;
    }
  }

  @Transactional
  public void updateCount(UpdateCartRequest request) {
    log.info("장바구니 수량 수정 요청: {}", request);
    
    // 해당 cartItem이 존재하는지 확인
    CartItem cartItem = cartRepository.findCartItemById(request.getCartItemId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장바구니 항목입니다."));
    
    // 사용자 검증
    if (!cartRepository.isCartItemOwnedByUser(request.getCartItemId(), request.getUserId())) {
        throw new IllegalArgumentException("해당 사용자의 장바구니 항목이 아닙니다.");
    }
    
    // 수량이 0 이하인 경우 삭제
    if (request.getProductCount() <= 0) {
        cartRepository.deleteCartItem(request.getCartItemId());
        return;
    }
    
    // 수량 업데이트
    cartRepository.updateCartItemCount(request.getCartItemId(), request.getProductCount());
  }

  @Transactional
  public void deleteItem(Long cartItemId) {
    log.info("장바구니 항목 삭제 요청: cartItemId={}", cartItemId);
    
    // 해당 cartItem이 존재하는지 확인
    if (!cartRepository.existsById(cartItemId)) {
        throw new IllegalArgumentException("존재하지 않는 장바구니 항목입니다.");
    }
    
    cartRepository.deleteCartItem(cartItemId);
  }

  @Transactional
  public void clearCart(String userId) {
    log.info("장바구니 전체 비우기 요청: userId={}", userId);
    
    Long cartId = cartRepository.findCartIdByUserId(userId);
    if (cartId == null) {
        throw new IllegalArgumentException("장바구니가 존재하지 않습니다.");
    }
    
    cartRepository.deleteAllCartItems(cartId);
  }
}
