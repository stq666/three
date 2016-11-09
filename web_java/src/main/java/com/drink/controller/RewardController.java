package com.drink.controller;

import com.alibaba.fastjson.JSONObject;
import com.drink.cornerstone.constant.BeanNames;
import com.drink.cornerstone.constant.ConstantElement;
import com.drink.cornerstone.constant.ControllerNames;
import com.drink.cornerstone.service.ServiceException;
import com.drink.module.Message;
import com.drink.module.Page;
import com.drink.service.RewardService;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 奖金控制层
 * Created by Administrator on 2016/5/14 0014.
 */
@Controller
@RequestMapping(ControllerNames.rewardController)
public class RewardController {
    @Resource(name= BeanNames.rewardService)
    RewardService rewardService;

}
