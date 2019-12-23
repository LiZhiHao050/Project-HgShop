package com.lizhihao.hgshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lizhihao.hgshop.dao.BrandMapper;
import com.lizhihao.hgshop.pojo.Brand;
import com.lizhihao.hgshop.service.BrandService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/10
 * Describe: 品牌服务实现类
 */

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandMapper brandMapper;

    // 查看列表
    @Override
    public PageInfo<Brand> selBrand(Brand brand, Integer pageNum) {
        PageHelper.startPage(pageNum, 3);
        List<Brand> blist = brandMapper.selBrand(brand);
        return new PageInfo<>(blist);
    }

    // 通过ID查询(修改用)
    @Override
    public Brand getBrandById(Integer id) {
        Brand brand = brandMapper.getBrandById(id);
        return brand;
    }

    // 添加品牌
    @Override
    public int addOrUpdBrand(Brand brand) {
        int res = 0;
        if (brand.getId() == null) {
            brand.setDeletedFlag(0);
            res = brandMapper.addBrand(brand);
        } else {
            res = brandMapper.updBrand(brand);
        }
        return res;
    }

    // 逻辑删除品牌
    @Override
    public int delBrand(String id) {
        int res = brandMapper.delBrand(id);
        return res;
    }

    // 修改品牌
    @Override
    public int updBrand(Brand brand) {
        int res = brandMapper.updBrand(brand);
        return res;
    }

    /**
     * 获取所有分类
     * @return
     */
    @Override
    public List<Brand> getAllBrands() {
        return brandMapper.getAllBrands();
    }
}
