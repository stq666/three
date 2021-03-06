package com.drink.service.impl;

import com.drink.cornerstone.constant.ConstantElement;
import com.drink.cornerstone.util.MD5;
import com.drink.cornerstone.util.StringUtil;
import com.drink.dao.SysUserMapper;
import com.drink.dao.ThreeGroupMapper;
import com.drink.dao.ThreeMemberMapper;
import com.drink.dao.ThreeRewardMapper;
import com.drink.model.SysUser;
import com.drink.model.ThreeGroup;
import com.drink.model.ThreeMember;
import com.drink.model.ThreeReward;
import com.drink.module.Page;
import com.drink.module.ThreeGroupVo;
import com.drink.module.ThreeMemberVo;
import com.drink.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/5/2 0002.
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService {
    @Autowired
    private  ThreeGroupMapper threeGroupMapper;
    @Autowired
    private ThreeMemberMapper threeMemberMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ThreeRewardMapper threeRewardMapper;



    @Override
    public void save(ThreeMemberVo vo) {
        Long memberId = saveThreeMember(vo);
        saveSysUser(memberId,vo);
        updateThreeGroupPid(vo.getGroupId(),vo.getGroupPid());
        saveThreeReward(memberId,vo.getGroupPid(),vo.getPid());
    }

    @Override
    public List<ThreeMemberVo> findThreeMemberByGroupId(Long groupId) {
        return threeMemberMapper.selectThreeMemberByGroupId(groupId);
    }

    @Override
    public Page<ThreeMemberVo> findPageThreeMemberByCondition(Page<ThreeMemberVo> page) {
        int start = page.getCurrentNum();
        int end=page.getPageSize()> ConstantElement.pageSize?ConstantElement.pageSize:page.getPageSize();
        ThreeMemberVo vo = page.getObj();
        int totalsize=threeMemberMapper.findCountByCondition(vo);
        page.calculate(totalsize, start, end);
        vo.setStart(page.getStartPos());
        vo.setLimit(page.getEndPos());
        List<ThreeMemberVo> list=threeMemberMapper.findDataByCondition(vo);
        if(list!=null && list.size()>0){
            for(ThreeMemberVo tm:list){
                if(tm==null){continue;}
                vo.setTotalReward(threeRewardMapper.selectTotalRewardByMid(vo.getId()));
            }
        }
        page.setDatas(list);
        return page;
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
        threeMemberMapper.save(member);
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
        sysUserMapper.save(user);
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
     * @param groupPid
     * @param pid
     */
    private void saveThreeReward(Long mid,Long groupPid,Long pid) {
        //添加推荐奖
        saveRecommendReward(mid,50,(byte)0);
        //添加层奖
        if(pid!=null){
            saveLayerReward(groupPid);
        }

    }



    /**
     * 添加推荐奖
     * @param mid
     */
    private void saveRecommendReward(Long mid,int money,byte type) {
        ThreeReward reward = new ThreeReward();
        reward.setMid(mid);
        reward.setMoney(money);
        reward.setCreatetime(new Date());
        reward.setType(type);
        threeRewardMapper.save(reward);
    }

    /**
     * 添加层奖励
     * @param oneGroupId
     */
    private void saveLayerReward(Long oneGroupId) {
        //第一层
        List<Long>oneMids = findMemberByGroupId(oneGroupId);
        Long twoGroupId = null;
        if(oneMids!=null && oneMids.size()>0){
            twoGroupId = saveReward(oneMids,80,(byte)1,oneGroupId);
        }
        //第二层
        Long threeGroupId = null;
        if(twoGroupId!=null){
            List<Long>twoMids = findMemberByGroupId(twoGroupId);
            if(twoMids!=null && twoMids.size()>0){
                threeGroupId = saveReward(twoMids,90,(byte)2,twoGroupId);
            }
        }
        //第三层
        Long fourGroupId = null;
        if(threeGroupId!=null){
            List<Long>threeMids = findMemberByGroupId(threeGroupId);
            if(threeMids!=null && threeMids.size()>0){
                fourGroupId = saveReward(threeMids,100,(byte)3,threeGroupId);
            }
        }
        //第四层
        Long fiveGroupId = null;
        if(fourGroupId!=null){
            List<Long>fourMids = findMemberByGroupId(fourGroupId);
            if(fourMids!=null && fourMids.size()>0){
                fiveGroupId = saveReward(fourMids,110,(byte)4,fourGroupId);
            }
        }
        //第五层
        Long sixGroupId = null;
        if(fiveGroupId!=null){
            List<Long>fiveMids = findMemberByGroupId(fiveGroupId);
            if(fiveMids!=null && fiveMids.size()>0){
                sixGroupId = saveReward(fiveMids,120,(byte)5,fiveGroupId);
            }
        }
        //第六层
        Long sevenGroupId = null;
        if(sixGroupId!=null){
            List<Long>sixMids = findMemberByGroupId(sixGroupId);
            if(sixMids!=null && sixMids.size()>0){
                sevenGroupId = saveReward(sixMids,130,(byte)6,sixGroupId);
            }
        }
        //第七层
        Long eightGroupId = null;
        if(sevenGroupId!=null){
            List<Long>sevenMids = findMemberByGroupId(sevenGroupId);
            if(sevenMids!=null && sevenMids.size()>0){
                eightGroupId = saveReward(sevenMids,140,(byte)7,sevenGroupId);
            }
        }
        //第八层
        if(eightGroupId!=null){
            List<Long>eightMids = findMemberByGroupId(eightGroupId);
            if(eightMids!=null && eightMids.size()>0){
                saveReward(eightMids,150,(byte)8,eightGroupId);
            }
        }
    }

    private Long saveReward(List<Long> mids, int reward,byte type,Long groupId) {
        for(Long mid:mids){
            if(mid==null){continue;}
            saveRecommendReward(mid,reward,type);
        }
        return threeGroupMapper.getPidByGroupId(groupId);
    }

    private List<Long> findMemberByGroupId(Long groupId) {
        return threeMemberMapper.selectIdsByGroupId(groupId);
    }
    @Override
    public String findMaxSerialNumber() {
        String maxSerialNumber = threeMemberMapper.selectMaxSerialNumber();
        if(StringUtil.isNull(maxSerialNumber)){
            return "30000";
        }
        return String.valueOf(Long.valueOf(maxSerialNumber)+1);
    }

    @Override
    public String getPserialnumber(String serialnumber) {
        return threeMemberMapper.selectPserialnumber(serialnumber);
    }

    @Override
    public void updatePassword(Long[] ids) {
        if(ids!=null && ids.length>0){
            for(Long id:ids){
                if(id==null){continue;}
                sysUserMapper.updatePasswordById(id,new MD5().getMD5ofStr("12345678"));
            }
        }
    }

}
