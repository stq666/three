package com.drink.module;

import com.drink.model.ThreeReward;

/**
 * Created by stq on 16-11-23.
 */
public class ThreeRewardVo extends ThreeReward {
    private Integer start;
    private Integer limit;
    private Integer totalMoney;

    public Integer getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Integer totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
