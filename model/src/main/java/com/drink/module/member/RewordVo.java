package com.drink.module.member;

import com.drink.model.Reward;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/9 0009.
 */
public class RewordVo extends Reward{
    private String name;//几级代理的名称
    private int totalMoney;
    private String memberName;//会员名称
    private String startDate;//
    private String endDate;
    private int start;
    private int limit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }
    public RewordVo(){

    }
    public RewordVo(String name,int totalMoney){
        this.name = name;
        this.totalMoney = totalMoney;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
