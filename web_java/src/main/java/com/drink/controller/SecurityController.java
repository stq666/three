package com.drink.controller;


import com.alibaba.fastjson.JSONObject;
import com.drink.cornerstone.constant.BeanNames;
import com.drink.cornerstone.constant.ConstantElement;
import com.drink.cornerstone.constant.ControllerNames;
import com.drink.cornerstone.security.PasswordService;
import com.drink.cornerstone.service.ServiceException;
import com.drink.cornerstone.util.MD5;
import com.drink.model.SysUser;
import com.drink.module.Message;
import com.drink.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登录和退出的控制类
 */
@Controller
@RequestMapping(ControllerNames.securityController)
public class SecurityController {
	Log logger = LogFactory.getLog(SecurityController.class);
    @Autowired
    PasswordService passwordService;
    @Resource(name = BeanNames.userService)
    UserService userService;

    @RequestMapping(value = ControllerNames.securityController_login ,method = RequestMethod.POST)
    public @ResponseBody
    JSONObject login(@ModelAttribute SysUser loginUser, HttpServletRequest request, HttpServletResponse response){
        JSONObject result=null;
        try{
            Subject subject = SecurityUtils.getSubject();
            if(subject.getSession().getAttribute(ConstantElement._USERINFO)!=null){  //session未失效
                SysUser user=(SysUser)subject.getSession().getAttribute(ConstantElement._USERINFO);
                result= new Message().getResult(true,false,user,null,null);
            }else{//session为空
                Session session = subject.getSession();
                SysUser user = userService.findAllByCondition(loginUser);
                if(user!=null){//查找到
                    session.setAttribute(ConstantElement._USERINFO, user);
                    session.setAttribute(ConstantElement._SERIALNUMBER,user.getLoginName());
                    session.setTimeout(3600000);
                    result = new Message().getResult(true,true,user,null,null);
                }else{
                    result = new Message().getResult(false,true,null,null,ConstantElement.securityUserError);
                }
            }
        }catch (Exception e){
            logger.error("登陆失败："+e.getMessage());
            String msg = e.getMessage();
            String tmpStr=null;
            if(e.getCause()!=null && e.getCause().getMessage()!=null){
                msg = e.getCause().getMessage();
            }
            if(msg.contains("doesnt exists")){
                tmpStr=ConstantElement.securityUserNotFind;
            }else if(msg.contains("did not match")){
                tmpStr=ConstantElement.securityUserError;
            }else{
                tmpStr=request.getCookies()[0].getValue();
                result= new Message().getResult(false,true,null,tmpStr,ConstantElement.errorCommonCode);
            }
            if(result==null)result= new Message().getResult(false,true,null,tmpStr,ConstantElement.errorForbidCode);
        }finally {
            return result;
        }
    }



}
