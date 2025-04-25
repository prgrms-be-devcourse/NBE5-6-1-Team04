package com.grepp.spring.app.model.cart.service;

import com.grepp.spring.app.controller.api.cart.payload.AddCartRequest;
import com.grepp.spring.app.controller.api.cart.payload.UpdateCartRequest;
import com.grepp.spring.app.model.cart.dto.CartItemDto;
import com.grepp.spring.app.model.cart.dto.CartItemResponse;
import com.grepp.spring.app.model.cart.dto.CartResponse;
import com.grepp.spring.app.model.cart.entity.CartItem;
import com.grepp.spring.app.model.cart.repository.CartRepository;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

  private final CartRepository cartRepository;

  @Transactional
  public void add(@Valid AddCartRequest request) {
    try {
      // 장바구니가 없으면 생성
      Long cartId = cartRepository.findCartIdByUserId(request.getUserId());
      if (cartId == null) {
        cartRepository.createCart(request.getUserId());
        cartId = cartRepository.findCartIdByUserId(request.getUserId());
      }
      
      // 이미 장바구니에 있는 상품인지 확인
      CartItem existingItem = cartRepository.findCartItemByCartIdAndProductId(cartId, request.getProductId());
      
      if (existingItem != null) {
        // 이미 있는 상품이면 수량만 증가
        int newCount = existingItem.getProductCount() + request.getProductCount();
        cartRepository.updateCartItemCount(existingItem.getCartItemId(), newCount);
      } else {
        // 새로운 상품이면 추가
        CartItem cartItem = new CartItem();
        cartItem.setCartId(cartId);
        cartItem.setProductId(request.getProductId());
        cartItem.setProductCount(request.getProductCount());
        cartRepository.insertCartItem(cartItem);
      }
      log.info("장바구니에 상품 추가 완료: cartId={}, productId={}", cartId, request.getProductId());
    } catch (Exception e) {
      log.error("장바구니 추가 중 오류 발생", e);
      throw e;
    }
  }

  public CartResponse getCart(String userId) {
    try {
      // 장바구니가 없으면 생성
      if (!cartRepository.existsCartByUserId(userId)) {
        cartRepository.createCart(userId);
      }
      
      List<CartItemDto> cartItems = cartRepository.findCartItemsByUserId(userId);
      CartResponse response = new CartResponse();
      response.setItems(cartItems.stream()
              .map(item -> {
                CartItemResponse res = CartItemResponse.from(item);
                // 각 상품의 총 가격 계산 (상품 가격 * 수량)
                res.setTotalPrice(item.getProductPrice() * item.getProductCount());
                return res;
              })
              .toList());
      
      // 전체 총액 계산
      response.setTotalPrice(cartItems.stream()
              .mapToInt(item -> item.getProductPrice() * item.getProductCount())
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
  public void deleteItem(Long cartItemId, String userId) {
    log.info("장바구니 항목 삭제 요청: cartItemId={}", cartItemId);
    
    // 사용자 검증
    if (!cartRepository.isCartItemOwnedByUser(cartItemId, userId)) {
        throw new IllegalArgumentException("해당 사용자의 장바구니 항목이 아닙니다.");
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
