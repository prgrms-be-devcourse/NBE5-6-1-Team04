package com.grepp.spring.app.model.cart.repository;

import com.grepp.spring.app.model.cart.dto.CartItemDto;
import com.grepp.spring.app.model.cart.entity.CartItem;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CartRepository {
    @Insert("INSERT INTO cart (user_id) VALUES (#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "cartId")
    void createCart(@Param("userId") String userId);

    @Select("SELECT cart_id FROM cart WHERE user_id = #{userId}")
    Long findCartIdByUserId(String userId);

    // (1) 해당 userId의 장바구니가 존재하는지 검증
    @Select("SELECT EXISTS (SELECT 1 FROM cart WHERE user_id = #{userId})")
    boolean existsCartByUserId(String userId);

    @Insert("INSERT INTO cart_item (cart_id, product_id, product_count) VALUES (#{cartId}, #{productId}, #{productCount})")
    void insertCartItem(CartItem cartItem);

    @Select("SELECT ci.cart_item_id as id, ci.cart_id as cartId, ci.product_id as productId, " +
            "ci.product_count as productCount, p.product_name as productName, " +
            "p.price as productPrice, " +
            "(SELECT name FROM product_image WHERE product_id = p.product_id LIMIT 1) as productImageUrl " +
            "FROM cart_item ci " +
            "JOIN cart c ON ci.cart_id = c.cart_id " +
            "JOIN product p ON ci.product_id = p.product_id " +
            "WHERE c.user_id = #{userId}")
    List<CartItemDto> findCartItemsByUserId(String userId);

    @Select("SELECT cart_item_id, cart_id, product_id, product_count FROM cart_item " +
            "WHERE cart_id = #{cartId} AND product_id = #{productId}")
    CartItem findCartItemByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);

    @Select("SELECT cart_item_id, cart_id, product_id, product_count FROM cart_item WHERE cart_item_id = #{cartItemId}")
    Optional<CartItem> findCartItemById(Long cartItemId);

    // (2) cart_item_id가 해당 userId의 장바구니에 속하는지(소유자인지) 검증
    @Select("SELECT EXISTS (SELECT 1 FROM cart_item ci " +
            "JOIN cart c ON ci.cart_id = c.cart_id " +
            "WHERE ci.cart_item_id = #{cartItemId} AND c.user_id = #{userId})")
    boolean isCartItemOwnedByUser(@Param("cartItemId") Long cartItemId, @Param("userId") String userId);

    @Update("UPDATE cart_item SET product_count = #{productCount} WHERE cart_item_id = #{cartItemId}")
    void updateCartItemCount(@Param("cartItemId") Long cartItemId, @Param("productCount") int productCount);

    @Delete("DELETE FROM cart_item WHERE cart_item_id = #{cartItemId}")
    void deleteCartItem(Long cartItemId);

    @Delete("DELETE FROM cart_item WHERE cart_id = #{cartId}")
    void deleteAllCartItems(Long cartId);

    @Select("SELECT EXISTS (SELECT 1 FROM cart_item WHERE cart_item_id = #{cartItemId})")
    boolean existsById(Long cartItemId);
}
