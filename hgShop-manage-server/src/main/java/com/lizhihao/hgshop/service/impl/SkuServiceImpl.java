package com.lizhihao.hgshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lizhihao.hgshop.dao.SkuMapper;
import com.lizhihao.hgshop.dao.SpecMapper;
import com.lizhihao.hgshop.pojo.Sku;
import com.lizhihao.hgshop.pojo.SkuSpec;
import com.lizhihao.hgshop.pojo.Spec;
import com.lizhihao.hgshop.service.SkuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author LZH
 * @date 2019/12/17
 * Describe:SKU服务实现层
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    SpecMapper specMapper;

    /**
     * 获取SKU列表
     * @param sku
     * @param pageNum
     * @return
     */
    @Override
    public PageInfo<Sku> skuList(Sku sku, Integer pageNum) {
        PageHelper.startPage(pageNum, 4);
        List<Sku> skus = skuMapper.skuList(sku);
        return new PageInfo<>(skus);
    }

    /**
     * 添加或修改
     * @param sku
     * @return
     */
    @Override
    public Integer saveOrUpdateSku(Sku sku) {
        int res = 0;
        if (sku.getId() == null) {
            sku.setStatus("0");
            sku.setCreateTime(new Date());
            sku.setUpdateTime(new Date());
            res = skuMapper.addSku(sku);
        } else {
            sku.setUpdateTime(new Date());
            res = skuMapper.updSku(sku);
            skuMapper.delSkuSpecBySkuIds(sku.getId().toString());
        }

        if (res > 0) {
            List<SkuSpec> skuSpec = sku.getSkuSpec();
            if (skuSpec != null) {
                for (SkuSpec spec : skuSpec) {
                    spec.setSkuId(sku.getId());
                    skuMapper.addSkuSpec(spec);
                }
            }

        }

        return res;
    }

    /**
     * 通过ID获取SKU
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getSkuById(Integer id) {
        Map<String, Object> map = new HashMap<>();
        // 获取SKU详情
        Sku sku = skuMapper.getSkuById(id);
        // 将中间表对象列表映像成规格参数ID列表
        List<Integer> collect = sku.getSkuSpec().stream().map(x -> x.getSpecId()).collect(Collectors.toList());
        // 根据规格参数ID列表获取规格参数及规格参数选项信息
        List<Spec> sepcList = specMapper.getSepcByIds(collect);
        map.put("sku", sku);
        map.put("specs", sepcList);

        return map;
    }


    /**
     * 删除SKU
     * @param ids
     * @return
     */
    @Override
    public Integer deleteSkuByIds(String ids) {
        int res = skuMapper.delSkuByIds(ids);

        if (res > 0) {
            skuMapper.delSkuSpecBySkuIds(ids);
        }

        return res;
    }


    /**
     * 首页获取最新商品
     * @param num 显示条数
     * @return
     */
    @Override
    public List<Sku> selectNews(int num) {
        List<Sku> list = skuMapper.selectNews(num);
        return list;
    }

    @Override
    public Map<String, Object> searchSkuById(Integer id) {
        Map<String, Object> map = new HashMap<>();

        // 通过id查询sku(不为多)
        Sku sku = skuMapper.searchSkuById(id);

        System.out.println("HZLH: " + sku);

        List<Integer> spuIds = new ArrayList<>();
        spuIds.add(sku.getSpuId());
        List<Spec> specs = specMapper.getSpecBySpuIds(spuIds);

        map.put("sku", sku);
        map.put("specs", specs);

        return map;
    }

    @Override
    public Map<String, Object> searchSkuBySpecOptionIds(Integer[] optionIds) {
        Map<String, Object> map = new HashMap<>();
        // 获取Sku的详情
        Sku sku = skuMapper.searchSkuBySpecOptionIds(optionIds);

        System.out.println("SSS:" + sku);

        //一般正常情况下sku不为空,即使没有该规格参数的组合,后台录入时,也会添加一份库存和价格为0的记录
        //这样处理是为了详情页展示图片及其他相关信息
        //库存为空，需在页面将规格选项置为禁用状态
        if (sku == null || sku.getStockCount() == 0) {
        }
        List<SkuSpec> skuSpecList = new ArrayList<>();
        for (int i = 0; i < optionIds.length; i++) {
            System.out.println("optionsID: " + optionIds[i]);
            SkuSpec skuSpec = new SkuSpec();
            skuSpec.setSpecOptionId(optionIds[i]);
            skuSpecList.add(skuSpec);
        }

        for (SkuSpec skuSpec : skuSpecList) {
            System.out.println("ABC: " + skuSpec);
        }

        sku.setSkuSpec(skuSpecList);
        //2.根据spu id获取规格参数及规格参数选项信息(查询该商品中有哪些规格参数)
        List<Integer> spuIds = new ArrayList<>();
        spuIds.add(sku.getSpuId());
        List<Spec> specList = specMapper.getSpecBySpuIds(spuIds);

        map.put("sku", sku);
        map.put("specs", specList);
        return map;
    }
}
