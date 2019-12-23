package com.lizhihao.hgshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lizhihao.hgshop.dao.SpecMapper;
import com.lizhihao.hgshop.dao.SpuMapper;
import com.lizhihao.hgshop.pojo.Brand;
import com.lizhihao.hgshop.pojo.Category;
import com.lizhihao.hgshop.pojo.Spec;
import com.lizhihao.hgshop.pojo.Spu;
import com.lizhihao.hgshop.service.SpuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LZH
 * @date 2019/12/14
 * Describe: SPU服务实现类
 */

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    SpuMapper spuMapper;

    @Autowired
    SpecMapper specMapper;

    /**
     * 获取列表
     * @param spu
     * @param pageNum
     * @return
     */
    @Override
    public PageInfo<Spu> spuList(Spu spu, Integer pageNum) {
        PageHelper.startPage(pageNum, 4);
        List<Spu> spus = spuMapper.spuList(spu);
        return new PageInfo<>(spus);
    }

    /**
     * 添加或修改
     * @param spu
     * @return
     */
    @Override
    public Integer saveOrUpdateSpu(Spu spu) {
        int res = 0;
        if (spu.getId() == null) {
            spu.setIsMarketable("0");
            res = spuMapper.addSpu(spu);
        } else {
            res = spuMapper.updateSpu(spu);
        }
        return res;
    }

    /**
     * 通过ID获取
     * @param id
     * @return
     */
    @Override
    public Spu getSpuById(Integer id) {
        Spu spuById = spuMapper.getSpuById(id);
        return spuById;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @Override
    public Integer deleteSpuByIds(String ids) {
        Integer res = spuMapper.deleteSpuByIds(ids);
        return res;
    }

    /**
     * 获取所有
     * @return
     */
    @Override
    public List<Spu> spus() {
        List<Spu> spus = spuMapper.selSpus();
        return spus;
    }

    /**
     * 首页查询
     * @param spu
     * @param pageNum
     * @return
     */
    @Override
    public Map<String, Object> getSpuList(Spu spu, Integer pageNum) {
        Map<String, Object> map = new HashMap<>();

        // 查询spuId的列表
        List<Integer> spuIds = spuMapper.selSpuIdsBySearch(spu);

        if (spuIds.size() == 0) { return map; }

        // 分页
        PageHelper.startPage(pageNum, 5);
        List<Spu> list = spuMapper.selSpuByIds(spuIds);
        PageInfo<Spu> pageInfo = new PageInfo<>(list);
        map.put("pageInfo", pageInfo);

        // 查询分类列表
        List<Category> categoryList = spuMapper.selCategoryBySpuIds(spuIds);
        // 查询商品列表
        List<Brand> brandList = spuMapper.selBrandBySpuIds(spuIds);
        // 查询规格参数列表
        List<Spec> specList = specMapper.getSepcByIds(spuIds);

        map.put("categoryList", categoryList);
        map.put("brandList", brandList);
        map.put("specList", specList);

        return map;
    }


}
