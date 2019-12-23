package com.lizhihao.hgshop.pojo;

import java.io.Serializable;

/**
 * @author LZH
 * @date 2019/12/16
 * Describe: SKU与规格参数关联的中间表
 */
public class SkuSpec implements Serializable {

    private static final long serialVersionUID = 1454164707678618661L;

    private Integer id;
    private Integer skuId;
    private Integer specId;
    private Integer specOptionId;

    public SkuSpec() {
        super();
    }

    public SkuSpec(Integer id, Integer skuId, Integer specId, Integer specOptionId) {
        super();
        this.id = id;
        this.skuId = skuId;
        this.specId = specId;
        this.specOptionId = specOptionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public Integer getSpecOptionId() {
        return specOptionId;
    }

    public void setSpecOptionId(Integer specOptionId) {
        this.specOptionId = specOptionId;
    }

    @Override
    public String toString() {
        return "SkuSpec [id=" + id + ", skuId=" + skuId + ", specId=" + specId + ", specOptionId=" + specOptionId + "]";
    }

}
