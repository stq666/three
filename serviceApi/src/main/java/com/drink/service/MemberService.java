package com.drink.service;

import com.drink.module.Page;
import com.drink.module.ThreeGroupVo;
import com.drink.module.ThreeMemberVo;

import java.util.List;

/**
 * Created by Administrator on 2016/5/2 0002.
 */
public interface MemberService {

    /**
     * 保存成员
     * @param vo
     */
    void save(ThreeMemberVo vo);

    /**
     * 获取每组成员
     * @param groupId
     * @return
     */
    List<ThreeMemberVo> findThreeMemberByGroupId(Long groupId);

    /**
     * 分页获取成员
     * @param page
     * @return
     */
    Page<ThreeMemberVo> findPageThreeMemberByCondition(Page<ThreeMemberVo> page);

    /**
     * 获取员工的最大编号
     * @return
     */
    public String findMaxSerialNumber();
}
