package com.lizhihao.hgshop.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author LZH
 * @date 2019/12/12
 * Describe: 规格参数实体类
 */
public class Spec implements Serializable {

    private static final long serialVersionUID = -5743751093711677870L;

    private Integer id;
    private String specName;                 // 规格参数名称
    private List<SpecOption> options;        // 规格参数列表

    private String optionNames;

    public Spec() {

    }

    public Spec(Integer id, String specName, List<SpecOption> options) {
        this.id = id;
        this.specName = specName;
        this.options = options;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public List<SpecOption> getOptions() {
        return options;
    }

    public void setOptions(List<SpecOption> options) {
        this.options = options;
    }

    public String getOptionNames() {
        return optionNames;
    }

    public void setOptionNames(String optionNames) {
        this.optionNames = optionNames;
    }

    @Override
    public String toString() {
        return "Spec{" +
                "id=" + id +
                ", specName='" + specName + '\'' +
                ", options=" + options +
                ", optionNames='" + optionNames + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spec spec = (Spec) o;
        return id.equals(spec.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
