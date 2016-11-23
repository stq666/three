package com.drink.dao;

import com.drink.daogen.ThreeRewardMapperGen;
import org.apache.ibatis.annotations.Param;

public interface ThreeRewardMapper extends ThreeRewardMapperGen {
    /**
     * 获取一个成员获得的全部奖金
     * @param mid
     * @return
     */
    Integer selectTotalRewardByMid(@Param("mid") Long mid);
}