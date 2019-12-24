package com.lizhihao.hgshop.service;

import com.github.pagehelper.PageInfo;
import com.lizhihao.hgshop.pojo.Category;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/23
 * Describe: 分类Redis服务接口
 */
public interface CategoryRedisService {

    PageInfo<Category> list(Category category, Integer pageNum);

    List<Category> getAllCategories();

    void addCategory(Category category, Integer type);

    Category getCategoryById(Integer id);

    void deleteCategory(Integer id);

}
