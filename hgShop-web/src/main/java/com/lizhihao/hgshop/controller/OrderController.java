package com.lizhihao.hgshop.controller;

import com.lizhihao.hgshop.pojo.Cart;
import com.lizhihao.hgshop.pojo.Order;
import com.lizhihao.hgshop.pojo.Sku;
import com.lizhihao.hgshop.service.CartService;
import com.lizhihao.hgshop.service.OrderService;
import com.lizhihao.hgshop.service.SkuService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LZH
 * @date 2019/12/24
 * Describe: 订单控制层
 */

@Controller
public class OrderController {

    @Reference(url="dubbo://localhost:20880",timeout=5000)
    private OrderService orderService;

    @Reference(url="dubbo://localhost:20890",timeout=5000)
    private CartService cartService;

    @Reference(url="dubbo://localhost:20880",timeout=5000)
    private SkuService skuService;

    /**
     * 订单页面
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping("/preOrder")
    public String preOrder(Integer[] ids, HttpServletRequest request) {
        List<Cart> cartList = cartService.preOrder(1, ids);
        Integer totalPrice = 0;
        Integer total = 0;

        for (Cart cart : cartList) {
            Sku sku = skuService.getSkuById2(cart.getSkuId());
            cart.setTitle(sku.getTitle());
            cart.setImage(sku.getImage());
            cart.setPrice(sku.getPrice());
            cart.setSubPrice(cart.getPrice() * cart.getPnum());
            totalPrice += cart.getSubPrice();
            total += cart.getPnum();
        }
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("total", total);
        request.setAttribute("postFee", 10);
        request.setAttribute("actualPrice", totalPrice+10);
        // 把列表传递给页面
        request.setAttribute("cartList", cartList);

        System.err.println("***************************************");
        System.err.println("total::" + total + ",totalPrice::" + totalPrice);
        System.err.println("cartList::" + cartList);
        System.err.println("***************************************");

        return "order";
    }

    /**
     * 创建订单
     * @param order
     * @param request
     * @return
     */
    @RequestMapping("/createOrder")
    public String createOrder(Order order, HttpServletRequest request) {
        orderService.createOrder(order);
        return "redirect:/myorder";
    }

    @RequestMapping("/myorder")
    public String orderList(Model model, String keyword, @RequestParam(defaultValue="1")Integer pageNum, @RequestParam(defaultValue="3")Integer pageSize) {
        //1.获取数据
        Integer userId = 1;
        Map<String, String> filter = new HashMap<>();
        filter.put("userId", userId+"");
        Map<String, Object> map = orderService.search(keyword, pageNum, filter);
        //2.填充数据
        model.addAttribute("map", map);
        model.addAttribute("keyword1", keyword);
        System.err.println(map);
        return "myorder";
    }

    @RequestMapping("/getOrderById")
    public String getOrderById(String id, HttpServletRequest request){
        Order order = orderService.getOrderById(id);
        request.setAttribute("order", order);
        return "order_detail";
    }

}
