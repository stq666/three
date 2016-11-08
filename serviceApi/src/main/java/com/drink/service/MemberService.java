package com.drink.service;

import com.drink.model.Member;
import com.drink.module.Page;
import com.drink.module.TreeNode;
import com.drink.module.member.MemberVo;
import com.drink.module.member.RewordVo;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/5/2 0002.
 */
public interface MemberService {
    /**
     * 分页获取会员信息
     * @param page
     * @param ifmanager 是否为管理员，0：否，1：是
     * @return
     */
    public Page<MemberVo> findPageMember(Page<MemberVo> page,byte ifmanager,String serialNumber);

    /**
     * 保存新注册的会员
     * @param vo
     */
    public void saveMember(MemberVo vo);

    /**
     * 获取员工的最大编号
     * @return
     */
    public String findMaxSerialNumber();

    /**
     * 根据会员主键获取会员的基本信息
     * @param id
     * @return
     */
    public MemberVo findLonginMember(Long id);

    /**
     * 根据会员主键，构造三级代理的架构图
     * @param id
     * @return
     */
    public List<TreeNode> findStructurlAllMemberById(Long id);

    /**
     * 根据编号获取一个人每天的奖金
     * @param serialnumber
     * @return
     */
    public List<RewordVo> showRewordDetail(String serialnumber);

    /**
     * 更改奖金状态
     * @param serialnumber
     */
    public void updateRewardStatus(String serialnumber);

    /**
     * 修改会员信息
     * @param vo
     */
    public void updateMember(MemberVo vo);

    /**
     * 重置密码
     * @param ids
     */
    public void updatePassword(Long[] ids);

    public List<RewordVo> findEveryDayMoney(String serialnumber);

    public void updateRewardStatusAndRewardtime(String serialnumber, String rewardtime);

    /**
     * 获取指定人的奖金，按日期分组
     * @param page
     * @return
     */
    Page<RewordVo> findPageReward(Page<RewordVo> page);
}
