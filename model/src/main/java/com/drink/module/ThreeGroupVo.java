package com.drink.module;

import com.drink.model.ThreeGroup;

/**
 * Created by stq on 16-11-16.
 */
public class ThreeGroupVo extends ThreeGroup {
    //组成员的名称
    private String memberName;
    private Integer start;
    private Integer limit;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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
}
