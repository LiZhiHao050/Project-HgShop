package com.lizhihao.hgshop.controller;

import com.lizhihao.hgshop.pojo.Cart;
import com.lizhihao.hgshop.service.CartService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/21
 * Describe: 购物车控制层
 */

@Controller
public class CartController {

	@Reference(url = "dubbo://localhost:20890", timeout = 5000)
    CartService cartService;

    @RequestMapping("/addCart")
	public String addCart(Integer skuId, Integer pnum) {
		//1.查询数据库中有没有该skuId对应的购物项
		Integer userId = 1;
		Cart cart = cartService.getCartByKey(userId, skuId);
		//2.如果有，就修改数量
		if (cart != null) {
			cartService.updateNum(cart.getId(), cart.getPnum() + pnum);
		} else {
			//3.如果没有，就插入
			cart = new Cart();
			cart.setSkuId(skuId);
			cart.setPnum(pnum);
			cart.setUid(1);
			cartService.addCart(cart);
		}
		return "redirect:/cartList";
	}

    @RequestMapping("/cartList")
	public String list(Model model) {
		Integer userId = 1;
		List<Cart> list = cartService.cartList(userId);

//		BigDecimal totalPrice = new BigDecimal(0);

		list.forEach(c -> {
			c.setSubPrice(c.getPrice() * c.getPnum());
//			totalPrice.add(c.getSubPrice());
		});
		model.addAttribute("total", list.size());
//		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", list);
		return "cart";
	}

	@RequestMapping("/updateNum")
	@ResponseBody
	public void updateNum(Integer id, Integer pnum) {
		cartService.updateNum(id, pnum);
	}

	@RequestMapping("/deleteCartItems")
	@ResponseBody
	public boolean deleteCartItems(Integer[] ids) {
		cartService.deleteCartItems(ids);
		return true;
	}

	@RequestMapping("/clearCart")
	public String clearCart() {
		Integer userId=1;
		cartService.clearCart(userId);
		return "redirect:/cartList";
	}

}
