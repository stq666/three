package com.drink.dao;

import com.drink.daogen.MemberMapperGen;
import com.drink.model.Member;
import com.drink.module.member.MemberVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberMapper extends MemberMapperGen {
    /**
     * 根据员工主键获取员工信息
     * @param id
     * @return
     */
    public MemberVo selectById(Long id);

    /**
     * 根据条件获取会员总数
     * @param vo
     * @return
     */
    public int findCountByCondition(MemberVo vo);

    /**
     * 根据条件获取分页的会员信息TODO
     * @param vo
     * @return
     */
    public List<MemberVo> findDataByCondition(MemberVo vo);

    /**
     * 查询新增的主键
     * @return
     */
    public Long selectId();

    /**
     * 获取最大的编号
     * @return
     */
    public String selectMaxSerialNumber();


    /**
     * 根据编号，查找此编号下的下级的个数
     * @param serialnumber
     * @return
     */
    public int selectCountBySerialNumber(@Param("serialnumber")String serialnumber);

    /**
     * 根据会员主键获取会员的信息
     * @param id
     * @return
     */
    public MemberVo selectLoginMember(@Param("id")Long id);

    /**
     * 修改会员的基本信息
     * @param vo
     */
    public void updateByMember(MemberVo vo);

    /**
     * 保存
     * @param member
     */
    void insertMember(Member member);
}