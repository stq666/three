package com.drink.dao;

import com.drink.daogen.ThreeRewardMapperGen;
import com.drink.model.ThreeReward;
import com.drink.module.ThreeRewardVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ThreeRewardMapper extends ThreeRewardMapperGen {
    /**
     * 获取一个成员获得的全部奖金
     * @param mid
     * @return
     */
    Integer selectTotalRewardByMid(@Param("mid") Long mid);

    int findCountByCondition(ThreeRewardVo vo);

    List<ThreeRewardVo> findDataByCondition(ThreeRewardVo vo);

    void save(ThreeReward reward);
}