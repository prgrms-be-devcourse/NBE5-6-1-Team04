package com.grepp.spring.app.model.product.repository;

import com.grepp.spring.app.model.product.dto.ProductDto;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductRepository {

  List<ProductDto> selectlistall();

  @Select("select * from product where product_id = #{id}")
  ProductDto selectProductDetail(int id);

  void insertproduct(ProductDto productDto);

  @Delete("delete from product where product_id = #{id} ")
  void deleteproduct(int id);
}
