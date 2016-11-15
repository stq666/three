package com.drink.service.impl;

import com.drink.cornerstone.util.MD5;
import com.drink.dao.SysUserMapper;
import com.drink.dao.ThreeGroupMapper;
import com.drink.dao.ThreeMemberMapper;
import com.drink.dao.ThreeRewardMapper;
import com.drink.model.SysUser;
import com.drink.model.ThreeGroup;
import com.drink.model.ThreeMember;
import com.drink.module.ThreeMemberVo;
import com.drink.service.MemberService;
import org.springframework.beans.BeanUtils;
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
    public void save(ThreeMemberVo vo) {
        Long memberId = saveThreeMember(vo);
        saveSysUser(memberId,vo);
        updateThreeGroupPid(vo.getGroupId(),vo.getGroupPid());
        saveThreeReward(vo.getGroupId(),vo.getGroupPid());
    }



    /**
     * 保存员工
     * @param vo
     * @return
     */
    private Long saveThreeMember(ThreeMemberVo vo) {
        ThreeMember member = new ThreeMember();
        member.setName(vo.getName());
        member.setSex(vo.getSex());
        member.setSerialnumber(vo.getSerialnumber());
        member.setIdcard(vo.getIdcard());
        member.setTelphone(vo.getTelphone());
        member.setCardno(vo.getCardno());
        member.setWechat(vo.getWechat());
        member.setQq(vo.getQq());
        member.setCreatetime(new Date());
        member.setStatus((byte)0);
        member.setAlipay(vo.getAlipay());
        member.setPid(vo.getPid());
        member.setGroupId(vo.getGroupId());
        threeMemberMapper.insert(member);
        return threeGroupMapper.selectId();
    }

    /**
     * 保存用户
     * @param memberId
     * @param vo
     */
    private void saveSysUser(Long memberId, ThreeMemberVo vo) {
        SysUser user = new SysUser();
        user.setLoginName(vo.getSerialnumber());
        user.setPassword(new MD5().getMD5ofStr("123qwe"));
        user.setStatus((byte)0);
        user.setIfManager((byte)0);
        user.setMemberId(memberId);
        user.setCreateTime(new Date());
        sysUserMapper.insert(user);
    }

    /**
     * 修改组的父组
     * @param groupId
     * @param groupPid
     */
    private void updateThreeGroupPid(Long groupId, Long groupPid) {
        threeGroupMapper.updatePidById(groupId,groupPid);
    }

    /**
     * 添加符合条件成员的奖金
     * @param groupId
     * @param groupPid
     */
    private void saveThreeReward(Long groupId, Long groupPid) {


    }

}
