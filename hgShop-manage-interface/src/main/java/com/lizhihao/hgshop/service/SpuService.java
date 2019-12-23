package com.lizhihao.hgshop.service;

import com.github.pagehelper.PageInfo;
import com.lizhihao.hgshop.pojo.Spu;

import java.util.List;
import java.util.Map;

/**
 * @author LZH
 * @date 2019/12/14
 * Describe: SPU服务接口
 */
public interface SpuService {

    PageInfo<Spu> spuList(Spu spu, Integer pageNum);

    Integer saveOrUpdateSpu(Spu spu);

    Spu getSpuById(Integer id);

    Integer deleteSpuByIds(String ids);

    List<Spu> spus();

    Map<String, Object> getSpuList(Spu spu, Integer pageNum);
}
