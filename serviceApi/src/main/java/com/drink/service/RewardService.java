package com.drink.service;

import com.drink.module.Page;
import com.drink.module.ThreeRewardVo;

/**
 * Created by Administrator on 2016/5/14 0014.
 */
public interface RewardService {
    /**
     * 分页显示一个员工的奖金数据
     * @param page
     * @return
     */
    Page<ThreeRewardVo> findPageThreeRewardByCondition(Page<ThreeRewardVo> page);
}
