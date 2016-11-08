package com.drink.module.member;

import com.drink.model.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/1 0001.
 */
public class MemberVo extends Member {
    private Integer start;
    private Integer limit;
    private String password;
    private Byte flag;
    private String pserialnumber;//上级编码
    private Long pid;//上级代理的编号
    private String pName;//上级的名称
    private int totalMoney=0;//每天的奖金
    private int totalNumber=0;//下级人数
    private List<String> allSerialNumber = new ArrayList<>();//自己三级代理人的编码集合
    private Byte rewardStatus;//奖金发放状态
    private Byte ifmanager;

    public Byte getIfmanager() {
        return ifmanager;
    }

    public void setIfmanager(Byte ifmanager) {
        this.ifmanager = ifmanager;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getFlag() {
        return flag;
    }

    public void setFlag(Byte flag) {
        this.flag = flag;
    }

    public String getPserialnumber() {
        return pserialnumber;
    }

    public void setPserialnumber(String pserialnumber) {
        this.pserialnumber = pserialnumber;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public List<String> getAllSerialNumber() {
        return allSerialNumber;
    }

    public void setAllSerialNumber(List<String> allSerialNumber) {
        this.allSerialNumber = allSerialNumber;
    }

    public Byte getRewardStatus() {
        return rewardStatus;
    }

    public void setRewardStatus(Byte rewardStatus) {
        this.rewardStatus = rewardStatus;
    }
}
