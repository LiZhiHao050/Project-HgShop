package com.lizhihao.hgshop.service;

import com.lizhihao.hgshop.pojo.Cart;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/21
 * Describe: 购物车接口
 */
public interface CartService {

    void addCart(Cart cart);

    List<Cart> cartList(Integer userId);

    void updateNum(Integer id, Integer pnum);

    void deleteCartItems(Integer[] ids);

    void clearCart(Integer userId);

    Cart getCartByKey(Integer userId, Integer skuId);

    List<Cart> preOrder(int i, Integer[] ids);

}
