package com.lizhihao.hgshop.service;

import com.github.pagehelper.PageInfo;
import com.lizhihao.hgshop.pojo.Spec;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/12
 * Describe: 规格参数服务接口
 */

public interface SpecService {

    /**
     * 规格参数列表
     * @param spec
     * @param pageNum
     * @return
     */
    PageInfo<Spec> specList(Spec spec, Integer pageNum);

    /**
     * 添加或修改规格参数
     * @param spec
     * @return
     */
    int addOrUpdSpec(Spec spec);

    /**
     * 删除规格参数 批量
     * @param id
     * @return
     */
    int delSpec(String id);

    int delSpecOption(String id);

    /**
     * 通过ID获取参数
     * @param id
     * @return
     */
    Spec getSpecById(Integer id);

    List<Spec> specs();

}
