package com.drink.cornerstone.constant;

/**
 * Created by ThinkPad on 2014/11/13.
 */
public interface ControllerNames {

    static final String controllerList="/list";
    static final String controllerSave="/save";
    static final String controllerDelete="/deletes";
    static final String controllerUpdate="/update";
    static final String controllerStart = "/start";//启用

    //验证码
    static final String codeController="/code";
    static final String codeController_imageShow="/imageShow";


    //登陆模块
    static final String securityController="/security";
    static final String securityController_login="/login";


    //员工
    static final String memberController="/member";
    static final String memberController_findMemberByGroupId="/findMemberByGroupId";
    static final String memberController_findPageThreeMemberByCondition="/findPageThreeMemberByCondition";
    static final String memberController_findMaxSerialNumber="/findMaxSerialNumber";
    static final String memberController_getPserialnumber="/getPserialnumber";


    //组
    static final String threeGroupController="/threeGroup";
    static final String threeGroupController_saveThreeGroup="/saveThreeGroup";
    static final String threeGroupController_findPageThreeGroupByCondition="/findPageThreeGroupByCondition";
    static final String threeGroupController_findStructurlByGroupId="/findStructurlByGroupId";

    //奖金
    static final String rewardController="/rewardController";
    static final String rewardController_findPageThreeRewardByCondition="/rewardController_findPageThreeRewardByCondition";



}
