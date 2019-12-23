package com.lizhihao.hgshop.dao;

import com.lizhihao.hgshop.pojo.Brand;
import com.lizhihao.hgshop.pojo.Category;
import com.lizhihao.hgshop.pojo.Spu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/14
 * Describe: SPU持久层
 */
public interface SpuMapper {

    List<Spu> spuList(Spu spu);

    Integer addSpu(Spu spu);

    Integer updateSpu(Spu spu);

    Spu getSpuById(Integer id);

    @Delete("DELETE FROM hg_spu WHERE id in (${ids})")
    Integer deleteSpuByIds(@Param("ids") String ids);

    @Select("SELECT id,goods_name FROM hg_spu")
    List<Spu> selSpus();

    /**
     * 查询Spu的ID
     * @param spu
     * @return
     */
    List<Integer> selSpuIdsBySearch(Spu spu);

    List<Spu> selSpuByIds(List<Integer> spuIds);

    List<Category> selCategoryBySpuIds(List<Integer> spuIds);

    List<Brand> selBrandBySpuIds(List<Integer> spuIds);
}
