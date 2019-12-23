package com.lizhihao.hgshop.dao;

import com.lizhihao.hgshop.pojo.Spec;
import com.lizhihao.hgshop.pojo.SpecOption;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/12
 * Describe:
 */
public interface SpecMapper {

    /**
     * 规格参数列表
     * @param spec
     * @return
     */
    List<Spec> specList(Spec spec);

    List<Spec> selSpecs();

    /**
     * 添加规格
     * @param spec
     * @return
     */
    int addSpec(Spec spec);

    int addSpecOption(SpecOption specOption);

    /**
     * 修改规格
     * @param spec
     * @return
     */
    int updSpec(Spec spec);

    int updSepcOption(SpecOption specOption);

    /**
     * 删除规格
     * @param id
     * @return
     */
    int delSpec(@Param("id") String id);

    /**
     * 单个删除规格参数选项
     * @param id
     * @return
     */
    @Delete("DELETE FROM hg_spec_option WHERE id = #{id}")
    int delSpecOption(@Param("id") String id);

    @Delete("DELETE FROM hg_spec_option WHERE spec_id in (${id})")
    int delSpecOptions(@Param("id") String id);

    /**
     * 根据ID查找规格
     * @param id
     * @return
     */
    Spec getSpecById(@Param("id") Integer id);

    @Select("SELECT id,spec_name FROM hg_spec")
    List<Spec> specs();

    List<Spec> getSepcByIds(List<Integer> specIds);

    List<Spec> getSpecBySpuIds(List<Integer> spuIds);
}
