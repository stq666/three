package com.drink.module;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/7 0007.
 */
public class TreeNode implements Serializable{

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
     * 注册日期
     */
    private String registerDate;
    private String serialNumber;

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

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
