package com.drink.dao;

import com.drink.daogen.MemberLevelMapperGen;
import com.drink.model.MemberLevel;
import com.drink.module.member.MemberVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberLevelMapper extends MemberLevelMapperGen {
    /**
     * 根据编码，查找下级人数，目前每个人直接下级最多3人
     * @param serialnumber   编码主键
     * @param bord     第几局，1：第一局，2：第二局，3：第三局
     * @return
     */
    public int selectCountBySerialNumber(@Param("serialnumber")String serialnumber,@Param("bord")byte bord);

    /**
     * 根据编码获取上级的编码
     * @param serialnumber
     * @return
     */
    public String selectPserialNumberBySerialNumber(@Param("serialnumber")String serialnumber);

    /**
     * 根据编码查找自己的直接下级
     * @param serialnumber
     * @return
     */
    public List<String> selectDirectSub(@Param("serialnumber")String serialnumber);

    /**
     * 根据编码获取自己直接代理的会员信息
     * @param serialnumber
     * @return
     */
    public List<MemberVo> selectMemberByPerialNumber(@Param("serialnumber")String serialnumber);

    void insertMemberLevel(MemberLevel ml);
}