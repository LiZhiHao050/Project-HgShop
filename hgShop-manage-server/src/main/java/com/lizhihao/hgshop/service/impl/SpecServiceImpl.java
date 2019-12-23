package com.lizhihao.hgshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lizhihao.hgshop.dao.SpecMapper;
import com.lizhihao.hgshop.pojo.Spec;
import com.lizhihao.hgshop.pojo.SpecOption;
import com.lizhihao.hgshop.service.SpecService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/12
 * Describe: 规格分类服务层
 */

@Service
public class SpecServiceImpl implements SpecService {

    @Autowired
    SpecMapper specMapper;

    /**
     * 规格分类列表
     * @param spec
     * @param pageNum
     * @return
     */
    @Override
    public PageInfo<Spec> specList(Spec spec, Integer pageNum) {
        PageHelper.startPage(pageNum, 4);
        List<Spec> specs = specMapper.specList(spec);
        return new PageInfo<>(specs);
    }

    /**
     * 添加或修改
     * @param spec
     * @return
     */
    @Override
    public int addOrUpdSpec(Spec spec) {
        int res = 0;
        if (spec.getId() == null) {
            res = specMapper.addSpec(spec);
        } else {
            res = specMapper.updSpec(spec);
        }

        if (res > 0) {
            List<SpecOption> options = spec.getOptions();
            for (SpecOption option : options) {
                option.setSpecId(spec.getId());       // 设置ID值
                if (StringUtils.isNotEmpty(option.getOptionName())) {
                    if (option.getId() == null) {         // 根据ID判断是否为添加或删除
                        specMapper.addSpecOption(option);
                    } else {
                        specMapper.updSepcOption(option);
                    }
                }
            }

        }

        return res;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public int delSpec(String id) {
        // 删除规格参数
        int res = specMapper.delSpec(id);
        // 删除相应的选项
        int num = specMapper.delSpecOptions(id);

        System.out.println("删除多个选项:" + num);
        return res;
    }

    @Override
    public int delSpecOption(String id) {
        int res = specMapper.delSpecOption(id);
        return res;
    }

    /**
     * 通过ID查找
     * @param id
     * @return
     */
    @Override
    public Spec getSpecById(Integer id) {
        Spec specById = specMapper.getSpecById(id);
        return specById;
    }

    @Override
    public List<Spec> specs() {
        return specMapper.specs();
    }
}
