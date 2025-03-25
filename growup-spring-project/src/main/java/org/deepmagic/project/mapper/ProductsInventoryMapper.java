package org.deepmagic.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.deepmagic.project.entity.ProductsInventory;

/**
 * ProductsInventoryMapper
 *
 * @author chenbin
 * @apiNote TODO
 * @since 2025/3/7 22:12
 */
@Mapper
public interface ProductsInventoryMapper {

    @Select("SELECT * FROM products_inventory WHERE product_id = #{product_id}")
    ProductsInventory get(@Param("product_id") Long product_id);

    @Update("UPDATE  products_inventory SET quantity = quantity -1 WHERE  quantity > 0 ")
    int buy(@Param("product_id") Long product_id);

}
