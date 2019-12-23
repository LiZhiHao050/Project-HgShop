package com.lizhihao.hgshop.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author LZH
 * @date 2019/12/11
 * Describe:
 */
public class Category implements Serializable {

    private static final long serialVersionUID = -3515588645017088057L;

    private Integer id;
    private Integer parentId;
    @JsonProperty("text")                   // 当返回Json类型时替换为text
    private String name;
    @JsonProperty("nodes")                  // 当返回Json类型时替换为nodes
    private List<Category> childs;
    private String parentName;
    private String path;

    // 用于在树中禁用选中属性
    private boolean selectable = true;

    public Category() {

    }

    public Category(Integer id, Integer parentId, String name, List<Category> childs, String parentName) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.childs = childs;
        this.parentName = parentName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getChilds() {
        return childs;
    }

    public void setChilds(List<Category> childs) {
        this.childs = childs;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", childs=" + childs +
                ", parentName='" + parentName + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id.equals(category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
