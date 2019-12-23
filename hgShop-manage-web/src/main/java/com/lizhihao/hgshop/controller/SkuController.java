package com.lizhihao.hgshop.controller;

import com.github.pagehelper.PageInfo;
import com.lizhihao.hgshop.pojo.Sku;
import com.lizhihao.hgshop.service.SkuService;
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
import java.util.Map;
import java.util.UUID;

/**
 * @author LZH
 * @date 2019/12/17
 * Describe: SKU控制层
 */

@Controller
@RequestMapping("sku")
public class SkuController {

    @Reference(url = "dubbo://localhost:20880", timeout = 5000)
    SkuService skuService;

    /**
     * SKU列表
     * @param model
     * @param sku
     * @param pageNum
     * @return
     */
    @RequestMapping("skuList")
    public String skuList(Model model, Sku sku, @RequestParam(defaultValue = "1")Integer pageNum) {
        PageInfo<Sku> pageInfo = skuService.skuList(sku, pageNum);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("sku", sku);

        return "SkuList";
    }

    /**
     * 增加或删除操作
     * @param sku
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("skuAdd")
    @ResponseBody
    public boolean skuAddOrUpd(Sku sku, MultipartFile file) throws Exception {

        String originName = file.getOriginalFilename();
        if (StringUtils.isNotBlank(originName)) {
            String fileName = UUID.randomUUID() + "_" + originName;
            File destFile = new File("Z://Pic/", fileName);
            // 如果上级目录不存在则创建
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }

            file.transferTo(destFile);

            // 如果原图片路径不为空则删除
            String oldPath = sku.getImage();
            if (StringUtils.isNotBlank(oldPath)) {
                FileUtils.forceDelete(new File("Z://Pic" + oldPath));
            }

            sku.setImage(fileName);
        }


        return skuService.saveOrUpdateSku(sku) > 0;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping("skuDelete")
    @ResponseBody
    public boolean skuDelete(String ids) {
        System.out.println("删除SKU的ID:" + ids);
        Integer res = skuService.deleteSkuByIds(ids);
        return res > 0;
    }

    /**
     * 修改
     */
    @RequestMapping("getSkuById")
    @ResponseBody
    public Map<String, Object> getSkuById(Integer id) {
        return skuService.getSkuById(id);
    }



}
