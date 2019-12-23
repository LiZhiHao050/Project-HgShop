package com.lizhihao.hgshop.dao;

import com.lizhihao.hgshop.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/11
 * Describe: 分类管理持久层
 */
public interface CategoryMapper {

    /**
     * 查看分类列表
     * @return
     */
    List<Category> getCategoryList(Category category);

    /**
     * 通过ID查询
     */
    Category getCatById(Integer id);

    /**
     * 添加分类
     * @param category
     * @return
     */
    @Insert("INSERT INTO hg_category (parent_id,name,path) VALUES (#{parentId},#{name},#{path})")
    int addCategory(Category category);

    /**
     * 删除分类
     * @param id
     * @return
     */
    @Delete("DELETE FROM hg_category WHERE id = #{id}")
    int delCategory(@Param("id") Integer id);

    /**
     * 修改分类
     * @param category
     * @return
     */
    @Update("UPDATE hg_category SET name = #{name},parent_id = #{parentId},#{path}=#{path} WHERE id = #{id}")
    int updCategory(Category category);

    /**
     * 获取所有分类
     * @return
     */
    List<Category> getAllCategories();

}
