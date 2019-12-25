package com.lizhihao.hgshop.controller;

import com.github.pagehelper.PageInfo;
import com.lizhihao.hgshop.pojo.Brand;
import com.lizhihao.hgshop.service.BrandService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/11
 * Describe:
 */

@Controller
@RequestMapping("brand")
public class BrandController {

    @Reference
    BrandService brandService;

    /**
     * 列表
     * @param model
     * @param pageNum   页码
     * @param brand     查询对象
     * @return
     */
    @RequestMapping("brandList")
    public String brandList(Model model, @RequestParam(defaultValue = "1")Integer pageNum, Brand brand) {
        PageInfo<Brand> pageInfo = brandService.selBrand(brand, pageNum);

        // 填充数据
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("brand", brand);

        System.out.println("W3C" + pageInfo.getList());
        return "BrandList";
    }


    /**
     * 添加品牌
     * @param brand
     * @return
     */
    @RequestMapping("addBrand")
    @ResponseBody
    public boolean addBrand(Brand brand) {
        int res = brandService.addOrUpdBrand(brand);
        return res > 0;
    }


    @RequestMapping("getBrandById")
    @ResponseBody
    public Brand getBrandById(Integer id) {
        Brand brand = brandService.getBrandById(id);
        return brand;
    }


    @RequestMapping("delBrand")
    @ResponseBody
    public boolean delBrand(String id) {
        System.out.println("DelData:" + id);
        int res = brandService.delBrand(id);
        System.out.println("删除:" + res);
        return res > 0;
    }

    /**
     * 获取所有品牌
     * @return
     */
    @RequestMapping("getAllBrands")
    @ResponseBody
    public List<Brand> getAllBrands() {
        List<Brand> brands = brandService.getAllBrands();
        System.out.println("abc" +brands);
        return brands;
    }

}
