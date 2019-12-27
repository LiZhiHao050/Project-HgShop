package com.lizhihao.hgshop.service;

import com.lizhihao.hgshop.pojo.Category;

/**
 * @author LZH
 * @date 2019/12/25
 * Describe: 分类使用Redis服务接口
 */
public interface RedisCategoryService {

    // 新增
    public void addCategory(Category category);

    // 删除
    public void delCategory(String ids);

    // 修改
    public void modCategory(Category category);

}
