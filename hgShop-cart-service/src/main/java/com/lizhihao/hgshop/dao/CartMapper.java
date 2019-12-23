package com.lizhihao.hgshop.dao;

import com.lizhihao.hgshop.pojo.Cart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/21
 * Describe:购物车持久层
 */
public interface CartMapper {

    @Insert("INSERT INTO hg_cart (uid,sku_id,pnum,create_time) VALUES (#{uid},#{skuId},#{pnum},#{createTime})")
    void insertCart(Cart cart);

    List<Cart> cartList(@Param("userId") int userId);

    @Update("update hg_cart set pnum = #{pnum} where id = #{id}")
    void updateNum(@Param("id")Integer id, @Param("pnum")Integer pnum);

    void deleteCartItems(Integer[] ids);

    @Delete("delete from hg_cart where uid = #{userId}")
    void deleteAll(@Param("userId") Integer userId);

    Cart selectCartByKey(@Param("userId")Integer userId, @Param("skuId")Integer skuId);

}
