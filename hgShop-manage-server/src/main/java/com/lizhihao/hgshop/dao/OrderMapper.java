package com.lizhihao.hgshop.dao;

import com.lizhihao.hgshop.pojo.Order;
import com.lizhihao.hgshop.pojo.OrderDetail;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/24
 * Describe: 订单持久层
 */
public interface OrderMapper {

    void insertOrder(Order order);

    void insertOrderDetail(OrderDetail orderDetail);

    Order selectOrderById(String orderId);

    List<Order> selectOrderList(Integer userId);

    List<OrderDetail> selectOrderDetailListByOrderId(String orderId);

}
