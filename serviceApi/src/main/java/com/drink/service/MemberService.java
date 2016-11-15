package com.drink.service;

import com.drink.model.ThreeGroup;
import com.drink.module.ThreeMemberVo;

/**
 * Created by Administrator on 2016/5/2 0002.
 */
public interface MemberService {
    /**
     * 保存抱团组
     * @param group
     */
    void saveThreeGroup(ThreeGroup group);

    /**
     * 保存成员
     * @param vo
     */
    void saveThreeMember(ThreeMemberVo vo);
}
