package com.drink.service;

import com.drink.module.Page;
import com.drink.module.member.RewordVo;

/**
 * Created by Administrator on 2016/5/14 0014.
 */
public interface RewardService {
    /**
     * 根据条件查询奖励
     * @param page
     * @return
     */
    public Page<RewordVo> findPageMoneyByCondition(Page<RewordVo> page);
}
