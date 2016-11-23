package com.drink.controller;

import com.alibaba.fastjson.JSONObject;
import com.drink.cornerstone.constant.BeanNames;
import com.drink.cornerstone.constant.ConstantElement;
import com.drink.cornerstone.constant.ControllerNames;
import com.drink.cornerstone.service.ServiceException;
import com.drink.model.ThreeReward;
import com.drink.module.Message;
import com.drink.module.Page;
import com.drink.module.ThreeMemberVo;
import com.drink.module.ThreeRewardVo;
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
    Log logger = LogFactory.getLog(RewardController.class);
    @Resource(name= BeanNames.rewardService)
    RewardService rewardService;

    /**
     * 分页显示一个员工的奖金数据
     * @param page
     * @param vo
     * @return
     */
    @RequestMapping(value = ControllerNames.rewardController_findPageThreeRewardByCondition ,method = RequestMethod.POST)
    public @ResponseBody JSONObject findPageThreeMemberByCondition(@ModelAttribute Page<ThreeRewardVo> page, @ModelAttribute ThreeRewardVo vo){
        Message msg=new Message();
        try {
            page.setObj(vo);
            page=rewardService.findPageThreeRewardByCondition(page);
            return msg.getResult(true,false,page,null,null);
        }catch (ServiceException e) {
            logger.error("分页显示一个员工的奖金数据时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error("分页显示一个员工的奖金数据时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }

}
