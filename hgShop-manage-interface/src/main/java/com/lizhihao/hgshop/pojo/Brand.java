package com.lizhihao.hgshop.pojo;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author LZH
 * @date 2019/12/10
 * Describe:商品类型实体类
 */
public class Brand implements Serializable {

    private static final long serialVersionUID = 3146300953088879157L;

    private Integer id;
    private String name;
    private String firstChar;
    private Integer deletedFlag;

    public Brand() {

    }

    public Brand(Integer id, String name, String firstChar, Integer deletedFlag) {
        this.id = id;
        this.name = name;
        this.firstChar = firstChar;
        this.deletedFlag = deletedFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }

    public Integer getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(Integer deletedFlag) {
        this.deletedFlag = deletedFlag;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstChar='" + firstChar + '\'' +
                ", deletedFlag=" + deletedFlag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return Objects.equals(id, brand.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
