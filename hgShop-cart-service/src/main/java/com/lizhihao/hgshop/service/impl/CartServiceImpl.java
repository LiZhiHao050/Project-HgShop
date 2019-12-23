package com.lizhihao.hgshop.service.impl;

import com.lizhihao.hgshop.dao.CartMapper;
import com.lizhihao.hgshop.pojo.Cart;
import com.lizhihao.hgshop.service.CartService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author LZH
 * @date 2019/12/21
 * Describe: 购物车接口实现层
 */

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartMapper cartMapper;

    /**
     * 添加到购物车
     * @param cart
     */
    @Override
    public void addCart(Cart cart) {
        cart.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        cartMapper.insertCart(cart);
    }

    /**
     * 购物车列表
     * @param userId
     * @return
     */
    @Override
    public List<Cart> cartList(Integer userId) {
        return cartMapper.cartList(userId);
    }

    /**
     * 修改数量
     * @param id
     * @param pnum
     */
    @Override
    public void updateNum(Integer id, Integer pnum) {
        cartMapper.updateNum(id, pnum);
    }

    /**
     * 删除购物项
     * @param ids
     */
    @Override
    public void deleteCartItems(Integer[] ids) {
        cartMapper.deleteCartItems(ids);
    }

    /**
     * 清空购物车
     * @param userId
     */
    @Override
    public void clearCart(Integer userId) {
        cartMapper.deleteAll(userId);
    }

    /**
     * 通过关键字获取
     * @param userId
     * @param skuId
     * @return
     */
    @Override
    public Cart getCartByKey(Integer userId, Integer skuId) {
        return cartMapper.selectCartByKey(userId, skuId);
    }

}
