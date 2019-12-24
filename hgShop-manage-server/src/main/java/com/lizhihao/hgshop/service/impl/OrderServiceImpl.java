package com.lizhihao.hgshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lizhihao.hgshop.dao.OrderMapper;
import com.lizhihao.hgshop.pojo.Order;
import com.lizhihao.hgshop.pojo.OrderDetail;
import com.lizhihao.hgshop.service.CartService;
import com.lizhihao.hgshop.service.OrderService;
import com.lizhihao.hgshop.service.SkuService;
import com.lizhihao.hgshop.util.ESUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LZH
 * @date 2019/12/24
 * Describe: 订单服务实现层
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Reference(url = "dubbo://localhost:20880", timeout = 5000)
    SkuService skuService;

    @Reference(url = "dubbo://localhost:20890", timeout = 5000)
    CartService cartService;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ElasticsearchTemplate esTemplate;



    @Override
    public String createOrder(Order order) {
        //1.生成订单号
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<5;i++){
            result+=random.nextInt(10);
        }
        order.setOrderId(date + result);
        //2.设置用户id
        order.setUserId(1);
        //3.订单状态(1:待支付 2:待收货 3.已发货 3.已收货 4.取消 5.待评价 6.已退货)
        order.setStatus(1);
        order.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //4.保存order
        orderMapper.insertOrder(order);

        //5.插入订单明细
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            orderDetail.setOrderId(order.getOrderId());
            //5.1.保存detail
            orderMapper.insertOrderDetail(orderDetail);
            //5.2.删除购物车已购买的购物项
            System.out.println("BYXCID: " + orderDetail.getSkuId());
            cartService.deleteCartItems(new Integer[]{Integer.parseInt(orderDetail.getSkuId())});
        }
        //6.将订单和订单明细存到ES中
        saveEsOrder(order);
        return order.getOrderId();
    }

    @Override
    public Order getOrderById(String orderId) {
        return orderMapper.selectOrderById(orderId);
    }

    @Override
    public PageInfo<Order> orderList(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectOrderList(userId);
        for (Order order : orderList) {
            List<OrderDetail> orderDetailList = orderMapper.selectOrderDetailListByOrderId(order.getOrderId());
            order.setOrderDetails(orderDetailList);
        }
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        return pageInfo;
    }

    /**
     * 高亮查询
     * @param keyword
     * @param pageNum
     * @param filter
     * @return
     */
    @Override
    public Map<String, Object> search(String keyword, Integer pageNum, Map<String, String> filter) {
        Integer pageSize = 4;
        Map<String, Object> map = new HashMap<>();
        AggregatedPage<Order> result = (AggregatedPage<Order>) ESUtils.selectObjects(esTemplate, Order.class, pageNum, pageSize, new String[]{"orderId", "orderDetails.title", "orderDetails.skuId"}, keyword, "orderId.keyword", filter);

        long total = result.getTotalElements();
        int totalPage = result.getTotalPages();
        List<Order> items = result.getContent();


        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        map.put("total", total);
        map.put("totalPage", totalPage);
        map.put("items", items);

        return map;
    }

    /**
     * 添加到ElasticSearch
     * @param order
     */
    @Override
    public void saveEsOrder(Order order) {
        ESUtils.saveObject(esTemplate, order.getOrderId() + "", order);
    }

}
