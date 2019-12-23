package com.lizhihao.hgshop.dao;

import com.lizhihao.hgshop.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/10
 * Describe:
 */
public interface BrandMapper {

    /**
     * 查询列表
     * @return
     */
    List<Brand> selBrand(Brand brand);

    /**
     * 通过ID查询
     * @param id
     * @return
     */
    Brand getBrandById(@Param("id") Integer id);

    @Insert("INSERT INTO hg_brand VALUES (null,#{name},#{firstChar},#{deletedFlag})")
    int addBrand(Brand brand);

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    int delBrand(@Param("id")String id);

    /**
     * 修改品牌
     * @param brand
     * @return
     */
    @Update("UPDATE hg_brand SET name=#{name},first_char=#{firstChar},deleted_flag=#{deletedFlag} WHERE id=#{id}")
    int updBrand(Brand brand);

    /**
     * 获取所有品牌
     * @return
     */
    List<Brand> getAllBrands();
}
