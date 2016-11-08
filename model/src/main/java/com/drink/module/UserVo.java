package com.drink.module;

import java.io.Serializable;

/**
 * Created by ThinkPad on 2015/4/29.
 */
public class UserVo implements Serializable{

    private Long id;
    private String name;
    private String email;
    private String companyName;//公司名称



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
