package com.drink.controller;

import com.alibaba.fastjson.JSONObject;
import com.drink.cornerstone.constant.BeanNames;
import com.drink.cornerstone.constant.ConstantElement;
import com.drink.cornerstone.constant.ControllerNames;
import com.drink.cornerstone.service.ServiceException;
import com.drink.cornerstone.util.StringUtil;
import com.drink.model.ThreeGroup;
import com.drink.module.*;
import com.drink.service.MemberService;
import com.drink.service.ThreeGroupService;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
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
     * 添加成员
     * @param vo
     * @return
     */
    @RequestMapping(value = ControllerNames.controllerSave ,method = RequestMethod.POST)
    public @ResponseBody JSONObject save(@ModelAttribute ThreeMemberVo vo){
        Message msg=new Message();
        try {
            memberService.save(vo);
            return msg.getResult(true,false,ConstantElement.commonSuccess,null,null);
        }catch (ServiceException e) {
            logger.error("添加抱团成员时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error("添加抱团成员时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }

    /**
     * 获取每组的成员
     * @param groupId
     * @return
     */
    @RequestMapping(value = ControllerNames.memberController_findMemberByGroupId ,method = RequestMethod.POST)
    public @ResponseBody JSONObject findMemberByGroupId(@RequestParam("groupId")Long groupId){
        Message msg=new Message();
        try {
            List<ThreeMemberVo>list = memberService.findThreeMemberByGroupId(groupId);
            return msg.getResult(true,false,list,null,null);
        }catch (ServiceException e) {
            logger.error("获取每组的成员时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error("获取每组的成员时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }

    /**
     * 分页获取成员
     * @param page
     * @param vo
     * @return
     */
    @RequestMapping(value = ControllerNames.memberController_findPageThreeMemberByCondition ,method = RequestMethod.POST)
    public @ResponseBody JSONObject findPageThreeMemberByCondition(@ModelAttribute Page<ThreeMemberVo> page, @ModelAttribute ThreeMemberVo vo){
        Message msg=new Message();
        try {
            page.setObj(vo);
            page=memberService.findPageThreeMemberByCondition(page);
            return msg.getResult(true,false,page,null,null);
        }catch (ServiceException e) {
            logger.error("分页获取成员时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error("分页获取成员时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }
    /**
     * 获取会员的最大编号
     * @return
     */
    @RequestMapping(value = ControllerNames.memberController_findMaxSerialNumber ,method = RequestMethod.POST)
    public @ResponseBody
    JSONObject findMaxSerialNumber(){
        Message msg=new Message();
        try {
            return msg.getResult(true,false,memberService.findMaxSerialNumber(),null,null);
        }catch (ServiceException e) {
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,e.getMessage(),ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }
    @RequestMapping(value = ControllerNames.memberController_getPserialnumber ,method = RequestMethod.POST)
    public @ResponseBody
    JSONObject getPserialnumber(@RequestParam("serialnumber")String serialnumber){
        Message msg=new Message();
        try {
            String pser = memberService.getPserialnumber(serialnumber);
            return msg.getResult(true,false,StringUtil.isNull(pser)?false:true,null,null);
        }catch (ServiceException e) {
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,e.getMessage(),ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }
    @RequestMapping(value = ControllerNames.memberController_resetPwd ,method = RequestMethod.POST)
    public @ResponseBody
    JSONObject resetPwd(@RequestParam("ids[]")Long[]ids){
        Message msg=new Message();
        try {
            memberService.updatePassword(ids);
            return msg.getResult(true,false,ConstantElement.commonSuccess,null,null);
        }catch (ServiceException e) {
            logger.error(e.getMessage());
            return msg.getResult(false,true,ConstantElement.commonError,e.getMessage(),ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }



}
