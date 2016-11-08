package com.drink.module;

/**
 * Created by Administrator on 2016/5/9 0009.
 */
public enum  RewardType {
    ONE(1,"一级代理"),TWO(2,"二级代理"),THREE(3,"三级代理"),OTHER(10,"额外奖励");
    private int key;
    private String value;
    private RewardType(int key,String value){
        this.key = key;
        this.value = value;
    }


}
