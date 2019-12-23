package com.lizhihao.hgshop.controller;

import com.github.pagehelper.PageInfo;
import com.lizhihao.hgshop.pojo.Spu;
import com.lizhihao.hgshop.service.SpuService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author LZH
 * @date 2019/12/13
 * Describe: SPU控制层
 */

@Controller
@RequestMapping("spu")
public class SpuController {

    @Reference(url = "dubbo://localhost:20880", timeout = 5000)
    SpuService spuService;

    /**
     * 获取分类树结构
     * @return
     */
    @RequestMapping("showCategoryTree")
    public String showCategoryTree() {
        return "CategoryTree";
    }

    /**
     * 展示SPU列表
     * @param model         model
     * @param categoryId    分类id
     * @param pageNum       页码
     * @param spu           模糊查询参数
     * @return              返回页面
     */
    @RequestMapping("spuList")
    public String spuList(Model model, Integer categoryId, @RequestParam(defaultValue = "1")Integer pageNum, Spu spu) {
        spu.setCategoryId(categoryId);

        PageInfo<Spu> spuPageInfo = spuService.spuList(spu, pageNum);

        model.addAttribute("pageInfo", spuPageInfo);
        model.addAttribute("spu", spu);

        return "SpuList";
    }

    /**
     * 添加spu
     * @param spu
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("spuAdd")
    @ResponseBody
    public boolean spuAdd(Spu spu, MultipartFile file) throws Exception {
        // 获取文件原名称
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isNotBlank(originalFilename)) {
            String fileName = originalFilename;
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File destFile = new File("Z://Pic/" + date + "/", fileName);

            // 如果上级目录不存在,则创建
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }

            // 复制文件内容
            file.transferTo(destFile);

            // 获取之前的图片将其删除
            String oldPath = spu.getSmallPic();
            if (StringUtils.isNotBlank(oldPath)) {
                FileUtils.forceDelete(new File("Z://Pic/" + oldPath));
            }

            spu.setSmallPic(date + "/" + fileName);

        }

        int res = spuService.saveOrUpdateSpu(spu);
        return res > 0;
    }

    /**
     * 删除SPU
     * @param ids   根据ID
     * @return      布尔类型是否成功
     */
    @RequestMapping("spuDelete")
    @ResponseBody
    public boolean spuDelete (String ids) {
        Integer res = spuService.deleteSpuByIds(ids);
        return res > 0;
    }

    /**
     * 通过ID查找
     * @param id
     * @return
     */
    @RequestMapping("getSpuById")
    @ResponseBody
    public Spu getSpuById(Integer id) {
        Spu spuById = spuService.getSpuById(id);
        return spuById;
    }


    @RequestMapping("spus")
    @ResponseBody
    public List<Spu> spus() {
        return spuService.spus();
    }


}
