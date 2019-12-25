package com.lizhihao.hgshop.controller;

import com.github.pagehelper.PageInfo;
import com.lizhihao.hgshop.pojo.Spec;
import com.lizhihao.hgshop.service.SpecService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author LZH
 * @date 2019/12/13
 * Describe: 规格参数控制层
 */

@Controller
@RequestMapping("spec")
public class SpecController {

    @Reference
    SpecService specService;

    /**
     * 规格参数列表
     * @param model
     * @param spec
     * @param pageNum
     * @return
     */
    @RequestMapping("specList")
    public String specList(Model model, Spec spec, @RequestParam(defaultValue = "1")Integer pageNum) {
        PageInfo<Spec> pageInfo = specService.specList(spec, pageNum);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("spec", spec);

        return "SpecList";
    }


    /**
     * 添加或修改方法
     * @param spec
     * @return
     */
    @RequestMapping("addOrUpdSpec")
    @ResponseBody
    public boolean addSpec(Spec spec) {
        int res = specService.addOrUpdSpec(spec);
        return res > 0;
    }

    /**
     * 删除规格参数
     * @param ids
     * @return
     */
    @RequestMapping("specDelete")
    @ResponseBody
    public boolean specDelete(String ids) {
        int res = specService.delSpec(ids);
        return  res > 0;
    }

    /**
     * 删除规格参数选项
     * @param id
     * @return
     */
    @RequestMapping("specOptionDelete")
    @ResponseBody
    public boolean specOptionDelete(String id) {
        int res = specService.delSpecOption(id);
        return res > 0;
    }

    /**
     * 通过ID查找规格参数
     * @param id
     * @return
     */
    @RequestMapping("getSpecById")
    @ResponseBody
    public Spec getSpecById(Integer id) {
        Spec specById = specService.getSpecById(id);
        return specById;
    }


    @RequestMapping("specs")
    @ResponseBody
    public List<Spec> specs() {
        return specService.specs();
    }

}
