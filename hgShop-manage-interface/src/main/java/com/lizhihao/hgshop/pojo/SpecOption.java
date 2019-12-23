package com.lizhihao.hgshop.pojo;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author LZH
 * @date 2019/12/12
 * Describe: 规格参数选项实体类
 */
public class SpecOption implements Serializable {

    private static final long serialVersionUID = 3918159051602287687L;

    private Integer id;
    private String optionName;          // 选项名称
    private Integer specId;             // 规格参数ID
    private Integer orders;             // 排序

    public SpecOption() {

    }

    public SpecOption(Integer id, String optionName, Integer specId, Integer orders) {
        this.id = id;
        this.optionName = optionName;
        this.specId = specId;
        this.orders = orders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "SpecOption{" +
                "id=" + id +
                ", optionName='" + optionName + '\'' +
                ", specId=" + specId +
                ", orders=" + orders +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecOption that = (SpecOption) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
