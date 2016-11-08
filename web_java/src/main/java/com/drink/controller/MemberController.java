package com.drink.controller;

import com.alibaba.fastjson.JSONObject;
import com.drink.cornerstone.constant.BeanNames;
import com.drink.cornerstone.constant.ConstantElement;
import com.drink.cornerstone.constant.ControllerNames;
import com.drink.cornerstone.service.ServiceException;
import com.drink.dao.RewardMapper;
import com.drink.model.Member;
import com.drink.model.Reward;
import com.drink.model.User;
import com.drink.module.Message;
import com.drink.module.Page;
import com.drink.module.TreeNode;
import com.drink.module.member.MemberVo;
import com.drink.module.member.RewordVo;
import com.drink.service.MemberService;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @RequestMapping(value = ControllerNames.memberController_findPageMember ,method = RequestMethod.POST)
    public @ResponseBody
    JSONObject findPageMember(@ModelAttribute Page<MemberVo> page,@ModelAttribute MemberVo vo){
        Message msg=new Message();
        try {
            Subject subject = SecurityUtils.getSubject();
            User user=((User)subject.getSession(true).getAttribute(ConstantElement._USERINFO));
            vo.setId(user.getMemberid());
            page.setObj(vo);
            page=memberService.findPageMember(page,user.getIfmanager(),user.getLoginname());
            return msg.getResult(true,false,page,null,null);
        }catch (ServiceException e) {
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,e.getMessage(),ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }

    /**
     * 获取指定人的奖金，按日期分组
     * @param page
     * @param vo
     * @return
     */
    @RequestMapping(value = ControllerNames.memberController_findPageReward ,method = RequestMethod.POST)
    public @ResponseBody
    JSONObject findPageReward(@ModelAttribute Page<RewordVo> page,@ModelAttribute RewordVo vo){
        Message msg=new Message();
        try {
            page.setObj(vo);
            page=memberService.findPageReward(page);
            return msg.getResult(true,false,page,null,null);
        }catch (ServiceException e) {
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,e.getMessage(),ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error(e.getMessage());
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

    /**
     * 根据编号查找每天的奖金
     * @param serialnumber
     * @return
     */
    @RequestMapping(value = ControllerNames.memberController_findEveryDayMoney ,method = RequestMethod.POST)
    public @ResponseBody
    JSONObject findEveryDayMoney(@RequestParam("serialnumber")String serialnumber){
        Message msg=new Message();
        try {
            List<RewordVo> list = memberService.findEveryDayMoney(serialnumber);
            return msg.getResult(true,false,list,null,null);
        }catch (ServiceException e) {
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,e.getMessage(),ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }

    /**
     * 获取当前登录人的信息
     * @return
     */
    @RequestMapping(value = ControllerNames.memberController_findLonginMember ,method = RequestMethod.POST)
    public @ResponseBody
    JSONObject findLonginMember(){
        Message msg=new Message();
        try {
            Subject subject = SecurityUtils.getSubject();
            User user=((User)subject.getSession(true).getAttribute(ConstantElement._USERINFO));
            MemberVo vo = memberService.findLonginMember(user.getMemberid());
            //获取当前登录的每天的奖励金额
            return msg.getResult(true,false,vo,null,null);
        }catch (ServiceException e) {
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,e.getMessage(),ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }

    /**
     * 注册会员或修改会员
     * @param vo
     * @return
     */
    @RequestMapping(value = ControllerNames.memberController_registerMember ,method = RequestMethod.POST)
    public @ResponseBody
    JSONObject registerMember(@ModelAttribute MemberVo vo){
        Message msg=new Message();
        try {
            Subject subject = SecurityUtils.getSubject();
            User user=((User)subject.getSession(true).getAttribute(ConstantElement._USERINFO));
            if(vo.getId()==null){
                vo.setCreateuser(user.getId());
                memberService.saveMember(vo);
            }else{
                memberService.updateMember(vo);
            }

            return msg.getResult(true,false,ConstantElement.commonSuccess,null,null);
        }catch (ServiceException e) {
            logger.error(e.getMessage());
            return msg.getResult(false,true,"此会员下已经有3个注册会员了，不能在此会员下注册了",e.getMessage(),ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }

    /**
     * 根据会员主键，构造三级代理的架构图
     * @param id
     * @return
     */
    @RequestMapping(value = ControllerNames.memberController_findStructurlAllMemberById ,method = RequestMethod.POST)
    public @ResponseBody
    JSONObject findStructurlAllMemberById(@RequestParam("id")Long id){
        Message msg=new Message();
        try {
            Subject subject = SecurityUtils.getSubject();
            User user=((User)subject.getSession(true).getAttribute(ConstantElement._USERINFO));
            if(id==null || id==-1){
                id = user.getMemberid();
            }
            List<TreeNode> list = memberService.findStructurlAllMemberById(id);
            return msg.getResult(true,false,list,null,null);
        }catch (ServiceException e) {
            logger.error(e.getMessage());
            return msg.getResult(false,true,"此会员下已经有3个注册会员了，不能在此会员下注册了",e.getMessage(),ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }

    /**
     * 根据编码查询此人当天奖金详情
     * @param serialnumber
     * @return
     */
    @RequestMapping(value = ControllerNames.memberController_showRewordDetail ,method = RequestMethod.POST)
    public @ResponseBody
    JSONObject showRewordDetail(@RequestParam("serialnumber")String serialnumber){
        Message msg=new Message();
        try {
            List<RewordVo> list = memberService.showRewordDetail(serialnumber);
            return msg.getResult(true,false,list,null,null);
        }catch (ServiceException e) {
            logger.error(e.getMessage());
            return msg.getResult(false,true,"此会员下已经有3个注册会员了，不能在此会员下注册了",e.getMessage(),ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }

    /**
     * 更改红包发放状态
     * @param serialnumber
     * @return
     */
    @RequestMapping(value = ControllerNames.memberController_updateRewardStatus ,method = RequestMethod.POST)
    public @ResponseBody
    JSONObject updateRewardStatus(@RequestParam("serialnumber")String serialnumber){
        Message msg=new Message();
        try {
            memberService.updateRewardStatus(serialnumber);
            return msg.getResult(true,false,ConstantElement.commonSuccess,null,null);
        }catch (ServiceException e) {
            logger.error(e.getMessage());
            return msg.getResult(false,true,ConstantElement.commonError,e.getMessage(),ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }
    @RequestMapping(value = ControllerNames.memberController_saveMoneyByRewardDate ,method = RequestMethod.POST)
    public @ResponseBody
    JSONObject saveMoneyByRewardDate(@RequestParam("serialnumber")String serialnumber,@RequestParam("rewardtime")String rewardtime){
        Message msg=new Message();
        try {
            memberService.updateRewardStatusAndRewardtime(serialnumber,rewardtime);
            return msg.getResult(true,false,ConstantElement.commonSuccess,null,null);
        }catch (ServiceException e) {
            logger.error(e.getMessage());
            return msg.getResult(false,true,ConstantElement.commonError,e.getMessage(),ConstantElement.errorForbidCode);
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
