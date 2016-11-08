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
    static final String securityController_logout="/logout";

    static final String securityController_findUserInfo="/findUserInfo";
    static final String securityController_updatePwd="/updatePwd";


    //员工
    static final String memberController="/member";
    static final String memberController_findPageMember="/findPageMember";
    static final String memberController_findPageReward="/findPageReward";
    static final String memberController_findMaxSerialNumber="/findMaxSerialNumber";
    static final String memberController_findLonginMember="/findLonginMember";
    static final String memberController_judgeLoginName="/judgeLoginName";
    static final String memberController_registerMember="/registerMember";
    static final String memberController_findStructurlAllMemberById="/findStructurlAllMemberById";
    static final String memberController_showRewordDetail="/showRewordDetail";
    static final String memberController_updateRewardStatus="/updateRewardStatus";
    static final String memberController_resetPwd="/resetPwd";
    static final String memberController_findEveryDayMoney="/findEveryDayMoney";
    static final String memberController_saveMoneyByRewardDate="/saveMoneyByRewardDate";
    //奖励
    static final String rewardController="/reward";
    static final String rewardController_findPageMoneyByCondition="/findPageMoneyByCondition";



}
