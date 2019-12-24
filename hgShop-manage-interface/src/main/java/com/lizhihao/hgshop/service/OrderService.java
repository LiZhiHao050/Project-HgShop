package com.lizhihao.hgshop.service;

import com.github.pagehelper.PageInfo;
import com.lizhihao.hgshop.pojo.Order;

import java.util.Map;

/**
 * @author LZH
 * @date 2019/12/24
 * Describe:
 */
public interface OrderService {

    /**
     * 生成订单
     *
     * @param order
     * @return
     */
    String createOrder(Order order);

    /**
     * 订单详情
     *
     * @param orderId
     * @return
     */
    Order getOrderById(String orderId);

    /**
     * 我的订单
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Order> orderList(Integer userId, Integer pageNum, Integer pageSize);

    Map<String, Object> search(String keyword, Integer pageNum, Map<String, String> filter);

    void saveEsOrder(Order order);

}
