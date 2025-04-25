package com.grepp.spring.app.model.cart.repository;

import com.grepp.spring.app.model.cart.dto.CartItemDto;
import com.grepp.spring.app.model.cart.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;


@Mapper
public interface CartRepository {
  void insertCartItem(CartItem cartItem);
  List<CartItemDto> findCartItemsByUserId(String userId);
  Long findCartIdByUserId(String userId);

  Optional<CartItem> findCartItemById(Long cartItemId);
  
  boolean isCartItemOwnedByUser(@Param("cartItemId") Long cartItemId, @Param("userId") String userId);
  
  void updateCartItemCount(@Param("cartItemId") Long cartItemId, @Param("productCount") int productCount);
  
  void deleteCartItem(Long cartItemId);
  
  void deleteAllCartItems(Long cartId);
  
  boolean existsById(Long cartItemId);
}
