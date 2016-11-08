package com.drink.cornerstone.tree;

import java.io.Serializable;
import java.util.List;

/**
 * 作用：tree的实体对象
 * author:邵天强
 * date:2014/11/15
 */
public class TreeNode implements Serializable{
    public  boolean  checked;


    private String name1;

    /**
     * tree的主键
     */
    private Long id;
    /**
     * 父id
     */
    private Long pId;
    /**
     * 名称
     */
    private String name;

    /**
     * 子
     */
    private List<TreeNode> children;
    /**
     * 是否为叶子节点
     */
    private boolean noLeaf;
    /**
     *
     */
    private List listdata;

    private  String icon;

    public Boolean getImgview() {
        return imgview;
    }

    public void setImgview(Boolean imgview) {
        this.imgview = imgview;
    }

    private Boolean imgview;
    /**
     * 部门id
     */
    private Long depid;
    public Long getDepid() {
        return depid;
    }

    public void setDepid(Long depid) {
        this.depid = depid;
    }
    private String ifleader;
    public String getIfleader() {
        return ifleader;
    }

    public void setIfleader(String ifleader) {
        this.ifleader = ifleader;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public boolean isNoLeaf() {
        return noLeaf;
    }

    public void setNoLeaf(boolean noLeaf) {
        this.noLeaf = noLeaf;
    }

    public TreeNode(Long id, Long pId,String name) {
        this.id = id;
        this.pId = pId;
        this.name = name;
    }
    public TreeNode(){}
    @Override
    public String toString() {
        return "TreeNode{" +
                "id=" + id +
                ", pId=" + pId +
                ", name='" + name + '\'' +
                ", children=" + children +
                ", noLeaf=" + noLeaf +
                '}';
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List getListdata() {
        return listdata;
    }

    public void setListdata(List listdata) {
        this.listdata = listdata;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }
}
