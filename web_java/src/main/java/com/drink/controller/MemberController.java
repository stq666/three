package com.drink.controller;

import com.alibaba.fastjson.JSONObject;
import com.drink.cornerstone.constant.BeanNames;
import com.drink.cornerstone.constant.ConstantElement;
import com.drink.cornerstone.constant.ControllerNames;
import com.drink.cornerstone.service.ServiceException;
import com.drink.module.Message;
import com.drink.module.Page;
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
     * 分页查询会员
     * @param page
     * @param vo
     * @return
     */
//    @RequestMapping(value = ControllerNames.memberController_findPageMember ,method = RequestMethod.POST)
//    public @ResponseBody
//    JSONObject findPageMember(@ModelAttribute Page<MemberVo> page,@ModelAttribute MemberVo vo){
//        Message msg=new Message();
//        try {
//            Subject subject = SecurityUtils.getSubject();
//            return msg.getResult(true,false,page,null,null);
//        }catch (ServiceException e) {
//            logger.error(e.getMessage());
//            return msg.getResult(false,true,null,e.getMessage(),ConstantElement.errorForbidCode);
//        }catch (Exception e){
//            logger.error(e.getMessage());
//            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
//        }
//    }




}
