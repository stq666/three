package com.drink.controller;

import com.alibaba.fastjson.JSONObject;
import com.drink.cornerstone.constant.BeanNames;
import com.drink.cornerstone.constant.ConstantElement;
import com.drink.cornerstone.constant.ControllerNames;
import com.drink.cornerstone.service.ServiceException;
import com.drink.cornerstone.util.StringUtil;
import com.drink.model.ThreeGroup;
import com.drink.module.Message;
import com.drink.module.Page;
import com.drink.module.ThreeMemberVo;
import com.drink.module.TreeNode;
import com.drink.service.MemberService;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 会员管理控制层
 * Created by Administrator on 2016/5/2 0002.
 */
@Controller
@RequestMapping(ControllerNames.memberController)
public class MemberController {
    @Resource(name= BeanNames.memberService)
    MemberService memberService;

    Log logger = LogFactory.getLog(MemberController.class);

    /**
     * 创建组
     * @param group
     * @return
     */
    @RequestMapping(value = ControllerNames.memberController_saveThreeGroup ,method = RequestMethod.POST)
    public @ResponseBody JSONObject saveThreeGroup(@ModelAttribute ThreeGroup group){
        Message msg=new Message();
        try {
            memberService.saveThreeGroup(group);
            return msg.getResult(true,false,ConstantElement.commonSuccess,null,null);
        }catch (ServiceException e) {
            logger.error("添加抱团组时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error("添加抱团组时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }
    /**
     * 创建组
     * @param vo
     * @return
     */
    @RequestMapping(value = ControllerNames.memberController_saveThreeMember ,method = RequestMethod.POST)
    public @ResponseBody JSONObject saveThreeMember(@ModelAttribute ThreeMemberVo vo){
        Message msg=new Message();
        try {
            memberService.saveThreeMember(vo);
            return msg.getResult(true,false,ConstantElement.commonSuccess,null,null);
        }catch (ServiceException e) {
            logger.error("添加抱团成员时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error("添加抱团成员时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }






}
