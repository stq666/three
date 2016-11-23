package com.drink.module;

import com.drink.model.ThreeMember;

/**
 * Created by stq on 16-11-9.
 */
public class ThreeMemberVo extends ThreeMember {
    private Long groupPid;
    private String pName;
    private Integer start;
    private Integer limit;
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Long getGroupPid() {
        return groupPid;
    }

    public void setGroupPid(Long groupPid) {
        this.groupPid = groupPid;
    }
}
