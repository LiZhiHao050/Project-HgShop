package com.lizhihao.hgshop.service;

import com.github.pagehelper.PageInfo;
import com.lizhihao.hgshop.pojo.Brand;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/10
 * Describe: 品牌服务接口层
 */
public interface BrandService {
    // 查看列表
    PageInfo<Brand> selBrand(Brand brand, Integer pageNum);

    /**
     *  通过ID查询(修改用)
     */
    Brand getBrandById(Integer id);

    // 添加品牌
    int addOrUpdBrand(Brand brand);

    // 逻辑删除品牌
    int delBrand(String id);

    // 修改品牌
    int updBrand(Brand brand);

    /**
     * 获取所有分类
     * @return
     */
    List<Brand> getAllBrands();
}
