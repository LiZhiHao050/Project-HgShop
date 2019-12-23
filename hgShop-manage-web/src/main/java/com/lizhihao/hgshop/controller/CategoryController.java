package com.lizhihao.hgshop.controller;

import com.github.pagehelper.PageInfo;
import com.lizhihao.hgshop.pojo.Category;
import com.lizhihao.hgshop.service.CategoryService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LZH
 * @date 2019/12/11
 * Describe:
 */

@Controller
@RequestMapping("category")
public class CategoryController {

    @Reference(url = "dubbo://localhost:20880", timeout = 5000)
    CategoryService categoryService;

    /**
     * 获取列表
     * @param model
     * @param category  模糊查询参数
     * @param pageNum   分页页码
     * @return
     */
    @RequestMapping("categoryList")
    public String categoryList(Model model, Category category, @RequestParam(defaultValue = "1") Integer pageNum) {
        PageInfo<Category> pageInfo = categoryService.getCategoryList(category, pageNum);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("category", category);

        return "CategoryList";
    }

    /**
     * 通过ID查询数据
     * @param id
     * @return
     */
    @RequestMapping("getCategoryById")
    @ResponseBody
    public Category getCatById(Integer id) {
        Category catById = categoryService.getCatById(id);
        return catById;
    }

    /**
     * 新增数据
     * @param category
     * @return
     */
    @RequestMapping("addCategory")
    @ResponseBody
    public boolean addCategory(Category category) {
        int res = categoryService.addOrUpdCategory(category);
        return res > 0;
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @RequestMapping("delCategory")
    @ResponseBody
    public Map<String, String> delCategory(Integer id) {
        Map<String, String> map = new HashMap<>();
        int res = categoryService.delCategory(id);

        if (res > 0) {
            map.put("code", "20010");
            map.put("msg", "删除成功!");
        } else {
            map.put("code", "500");
            map.put("msg", "删除失败!");
        }

        return map;
    }

    /**
     * 获取所有的分类
     */
    @RequestMapping("getAllCategories")
    @ResponseBody
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * SPU管理页面显示树(一级二级分类不能选中处理)
     * @return
     */
    @RequestMapping("getAllCategoriesTree")
    @ResponseBody
    public List<Category> getCategoryTree() {
        List<Category> list = categoryService.getAllCategories();
        list.forEach(x -> {
            x.setSelectable(false);
            x.getChilds().forEach(y -> y.setSelectable(false));
        });
        list.forEach(System.err::println);
        return list;
    }


}
