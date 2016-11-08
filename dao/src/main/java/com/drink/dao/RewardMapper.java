package com.drink.dao;

import com.drink.daogen.RewardMapperGen;
import com.drink.model.Reward;
import com.drink.module.member.RewordVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RewardMapper extends RewardMapperGen {
    /**
     * 根据会员的编码获取此会员当天的收入
     * @param serialnumber
     * @param rewardtime
     * @return
     */
    public List<Reward> selectBySerialNumber(@Param("serialnumber")String serialnumber, @Param("rewardtime")String rewardtime);

    /**
     * 更改红包状态
     * @param serialnumber
     * @param rewardtime
     */
    public void updateStatusBySerialnumber(@Param("serialnumber")String serialnumber, @Param("rewardtime")String rewardtime);

    /**
     * 按照条件查询每天的奖金数量
     * @param vo
     * @return
     */
    public int findCountByCondition(RewordVo vo);

    public List<RewordVo> findDataByCondition(RewordVo vo);

    public List<RewordVo> selectEveryDayMoney(@Param("serialnumber")String serialnumber);

    void insertReward(Reward reward);

    /**
     * 获取所有的奖金
     * @param serialnumber
     * @return
     */
    Integer findAllRewardBySerialNumber(@Param("serialnumber")String serialnumber);

    public int findCountBySerialNumber(RewordVo vo);

    public List<RewordVo> findDataBySerialNumber(RewordVo vo);
}