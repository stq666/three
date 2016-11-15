package com.drink.service.impl;

import com.drink.dao.SysUserMapper;
import com.drink.dao.ThreeGroupMapper;
import com.drink.dao.ThreeMemberMapper;
import com.drink.dao.ThreeRewardMapper;
import com.drink.model.ThreeGroup;
import com.drink.module.ThreeMemberVo;
import com.drink.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Administrator on 2016/5/2 0002.
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService {

    @Autowired
    private ThreeGroupMapper threeGroupMapper;
    @Autowired
    private ThreeMemberMapper threeMemberMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ThreeRewardMapper threeRewardMapper;


    @Override
    public void saveThreeGroup(ThreeGroup group) {
        //获取最大的顺序
        int maxSort = threeGroupMapper.getMaxSort();
        group.setGroupSort(maxSort++);
        group.setCreateTime(new Date());
        threeGroupMapper.insert(group);
    }

    @Override
    public void saveThreeMember(ThreeMemberVo vo) {

    }
}
