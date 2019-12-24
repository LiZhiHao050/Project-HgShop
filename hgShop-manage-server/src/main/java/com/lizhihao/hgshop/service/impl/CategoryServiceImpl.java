package com.lizhihao.hgshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.lizhihao.hgshop.dao.CategoryMapper;
import com.lizhihao.hgshop.pojo.Category;
import com.lizhihao.hgshop.service.CategoryService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/11
 * Describe: 分类服务实现层
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 获取列表
     *
     * @return
     */
    @Override
    public PageInfo<Category> getCategoryList(Category category, Integer pageNum) {
        PageHelper.startPage(pageNum, 4);
        List<Category> categoryList = categoryMapper.getCategoryList(category);
        return new PageInfo<>(categoryList);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @Override
    public Category getCatById(Integer id) {
        Category catById = categoryMapper.getCatById(id);
        return catById;
    }

    /**
     * 增加分类
     *
     * @param category
     * @return
     */
    @Override
    public int addOrUpdCategory(Category category) {
        /*int res = 0;

        String path = "";

        Category firstC = categoryMapper.getCatById(category.getParentId());
        if (firstC.getParentId() != 0) {
            Integer flag = firstC.getParentId();
            path = firstC.getName();

            while (flag != 0) {
                Category tong = categoryMapper.getCatById(flag);
                flag = tong.getParentId();
                path = tong.getName() + "/" + path;
            }
            path = path + "/" + category.getName();

            category.setPath(path);
        }

        if (category.getId() == null) {
            res = categoryMapper.addCategory(category);
        } else {
            res = categoryMapper.updCategory(category);
        }
        return res;*/

        Integer count = null;
        if (category.getId() == null) {
            //新增
            System.out.println("FID:" + category.getParentId());
            if (category.getParentId() == null) {
                category.setParentId(0);
            }
            count = categoryMapper.addCategory(category);
            //测试redis用,使用kafka后就没用了
//			categoryRedisService.addCategory(category, 1);
            Gson gson = new Gson();
            //消息:<k,v>对
            //k:区分是新增/修改/删除
            //k:1 新增
            //k:2修改
            //k:3删除
            kafkaTemplate.sendDefault("1", gson.toJson(category));
        } else {
            //修改
            count = categoryMapper.updCategory(category);
            //测试redis用,使用kafka后就没用了
            //categoryRedisService.addCategory(category, 2);
            Gson gson = new Gson();
            kafkaTemplate.sendDefault("2", gson.toJson(category));
        }

        return count;

    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @Override
    public int delCategory(Integer id) {
        int res = categoryMapper.delCategory(id);
        return res;
    }

    /**
     * 修改分类
     *
     * @param category
     * @return
     */
    @Override
    public int updCategory(Category category) {
        int res = categoryMapper.updCategory(category);
        return res;
    }

    /**
     * 获取所有分类
     *
     * @return
     */
    @Override
    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

}
