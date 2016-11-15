package com.drink.module;

import com.drink.model.ThreeMember;

/**
 * Created by stq on 16-11-9.
 */
public class ThreeMemberVo extends ThreeMember {
    private Long groupPid;

    public Long getGroupPid() {
        return groupPid;
    }

    public void setGroupPid(Long groupPid) {
        this.groupPid = groupPid;
    }
}
