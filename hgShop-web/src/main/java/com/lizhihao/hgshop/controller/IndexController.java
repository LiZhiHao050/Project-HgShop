package com.lizhihao.hgshop.controller;

import com.lizhihao.hgshop.pojo.Category;
import com.lizhihao.hgshop.pojo.Sku;
import com.lizhihao.hgshop.pojo.Spu;
import com.lizhihao.hgshop.service.CategoryService;
import com.lizhihao.hgshop.service.SkuService;
import com.lizhihao.hgshop.service.SpuService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LZH
 * @date 2019/12/18
 * Describe:
 */

@Controller
public class IndexController {

    @Reference
    CategoryService categoryService;

    @Reference
    SpuService spuService;

    @Reference
    SkuService skuService;

    /**
     * 商城首页
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String index(Model model) {
        // 查询导航中的数据
        List<Category> navCategories = categoryService.getAllCategories();

        // 查询最新商品数据(4条)
        List<Sku> newSkus = skuService.selectNews(4);

        model.addAttribute("navCategories", navCategories);
        model.addAttribute("newSkus", newSkus);

        return "index";
    }

    /**
     * 搜索页
     * @param spu
     * @param keyword      关键字
     * @param brandId
     * @param categoryId
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping("list")
    public String list(Spu spu, String keyword, Integer brandId, Integer categoryId,
                       @RequestParam(defaultValue = "1") Integer pageNum, Model model) {
        // 设置关键字到对应变量接收
        spu.setGoodsName(keyword);
        spu.setBrandId(brandId);
        spu.setCategoryId(categoryId);

        Map<String, Object> map = spuService.getSpuList(spu, pageNum);

        model.addAttribute("map", map);
        model.addAttribute("keysword", keyword);
        return "list";
    }


    @RequestMapping("page")
    public String page(Model model, Integer id, Integer[] optionIds) {
        Map<String, Object> map = new HashMap<>();
        if (id != null) {
            // 通过sku的id查询
            map = skuService.searchSkuById(id);
        } else {
            // 通过规格参数选项ids查询
            map = skuService.searchSkuBySpecOptionIds(optionIds);
        }

        model.addAttribute("map", map);
        return "page";
    }

}
