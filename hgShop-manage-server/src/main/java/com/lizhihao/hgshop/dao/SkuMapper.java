package com.lizhihao.hgshop.dao;

import com.lizhihao.hgshop.pojo.Sku;
import com.lizhihao.hgshop.pojo.SkuSpec;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/17
 * Describe: SKU持久层
 */
public interface SkuMapper {

    List<Sku> skuList(Sku sku);

    int addSku(Sku sku);

    int updSku(Sku sku);

    void addSkuSpec(SkuSpec skuSpec);

    void updSkuSpec(SkuSpec skuSpec);

    Sku getSkuById(@Param("id") Integer id);

    @Delete("DELETE FROM hg_sku WHERE id in (${ids})")
    int delSkuByIds(@Param("ids") String ids);

    @Delete("DELETE FROM hg_sku_spec WHERE sku_id in (${ids})")
    void delSkuSpecBySkuIds(@Param("ids") String ids);

    List<Sku> selectNews(@Param("num") int num);

    // 详情页
    Sku searchSkuById(Integer id);

    Sku searchSkuBySpecOptionIds(Integer[] optionIds);
}
