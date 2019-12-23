package com.lizhihao.hgshop.service;

import com.github.pagehelper.PageInfo;
import com.lizhihao.hgshop.pojo.Category;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/11
 * Describe: 分类服务接口
 */
public interface CategoryService {

    /**
     * 查看分类列表
     * @return
     */
    PageInfo<Category> getCategoryList(Category category, Integer pageNum);

    /**
     * 通过ID查询
     */
    Category getCatById(Integer id);

    /**
     * 添加分类
     * @param category
     * @return
     */
    int addOrUpdCategory(Category category);

    /**
     * 删除分类
     * @param id
     * @return
     */
    int delCategory(Integer id);

    /**
     * 修改分类
     * @param category
     * @return
     */
    int updCategory(Category category);

    /**
     * 获取所有分类
     * @return
     */
    List<Category> getAllCategories();
}
