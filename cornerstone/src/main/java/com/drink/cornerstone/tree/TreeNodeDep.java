package com.drink.cornerstone.tree;

import java.io.Serializable;
import java.util.List;

/**
 * 作用：tree的实体对象
 * author:邵天强
 * date:2014/11/15
 */
public class TreeNodeDep implements Serializable{
    public  boolean  checked;

    /**
     * tree的主键
     */
    private String id;
    private String icon;



    /**
     * 父id
     */
    private String pId;
    /**
     * 名称
     */
    private String name;

    /**
     * 子
     */
    private List<TreeNodeDep> children;
    /**
     * 是否为叶子节点
     */
    private boolean noLeaf;
    /**
     * 是否为部门
     */
    private Boolean ifDep;

    private Integer indexNumber;


    public Integer getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(Integer indexNumber) {
        this.indexNumber = indexNumber;
    }

    public Boolean getIfDep() {
        return ifDep;
    }

    public void setIfDep(Boolean ifDep) {
        this.ifDep = ifDep;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    private boolean checkbox;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeNodeDep> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNodeDep> children) {
        this.children = children;
    }

    public boolean isNoLeaf() {
        return noLeaf;
    }

    public void setNoLeaf(boolean noLeaf) {
        this.noLeaf = noLeaf;
    }

    public TreeNodeDep(String id, String pId, String name,Integer indexNumber) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.indexNumber = indexNumber;
    }
    public TreeNodeDep(){}
    @Override
    public String toString() {
        return "TreeNode{" +
                "id=" + id +
                ", pId=" + pId +
                ", name='" + name + '\'' +
                ", children=" + children +
                ", noLeaf=" + noLeaf +
                ", ifDep=" + ifDep +
                ", indexNumber=" + indexNumber +
                '}';
    }
    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
