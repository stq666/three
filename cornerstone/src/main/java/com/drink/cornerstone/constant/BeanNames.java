package com.drink.cornerstone.constant;

/**
 * Spring 配置文件的Bean Name常量
 * Created by newroc on 13-11-11.
 */
public interface BeanNames {
    String REQUEST_DISPATCHER = "requestDispatcher";
    String REST_RPC_HELPER="appRestRpcHelper";
    static final String beanService="beanService";
    static final String userService="userService";
    static final String memberService="memberService";
    static final String rewardService="rewardService";

    //定时执行
    static final String subQuartzService="subQuartzService";

    //认可
    static final String subRecognitionService="subRecognitionService";
    //部门
    static final String subOrganizationService="subOrganizationService";





}
