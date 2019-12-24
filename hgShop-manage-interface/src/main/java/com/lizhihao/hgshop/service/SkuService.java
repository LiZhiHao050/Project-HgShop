package com.lizhihao.hgshop.service;

import com.github.pagehelper.PageInfo;
import com.lizhihao.hgshop.pojo.Sku;

import java.util.List;
import java.util.Map;

/**
 * @author LZH
 * @date 2019/12/17
 * Describe:SKU服务接口层
 */
public interface SkuService {

    PageInfo<Sku> skuList(Sku sku, Integer pageNum);

    Integer saveOrUpdateSku(Sku sku);

    Map<String, Object> getSkuById(Integer id);

    Integer deleteSkuByIds(String ids);

    List<Sku> selectNews(int i);

    Map<String, Object> searchSkuById(Integer id);

    Map<String, Object> searchSkuBySpecOptionIds(Integer[] optionIds);

    Sku getSkuById2(Integer skuId);

}
