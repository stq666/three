package com.drink.controller;


import com.alibaba.fastjson.JSONObject;
import com.drink.cornerstone.constant.BeanNames;
import com.drink.cornerstone.constant.ConstantElement;
import com.drink.cornerstone.constant.ControllerNames;
import com.drink.cornerstone.security.PasswordService;
import com.drink.cornerstone.service.ServiceException;
import com.drink.model.User;
import com.drink.module.Message;
import com.drink.module.member.MemberVo;
import com.drink.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    JSONObject login(@RequestParam("loginName")String loginName,@RequestParam("pwd")String pwd,HttpServletRequest request,HttpServletResponse response){
        JSONObject result=null;
        try{
            Subject subject = SecurityUtils.getSubject();
            if(subject.getSession().getAttribute(ConstantElement._USERINFO)!=null){  //session未失效
                User user=(User)subject.getSession().getAttribute(ConstantElement._USERINFO);
                result= new Message().getResult(true,false,user,null,null);
            }else{//session为空
                Session session = subject.getSession();
                Map map = userService.findUserByNameAndPwd(loginName,pwd);
                if(map!=null && map.size()>0){//查找到
                    if(map.get("user")!=null){
                        User user = (User) map.get("user");
                        session.setAttribute(ConstantElement._USERINFO, user);
                        session.setAttribute(ConstantElement._SERIALNUMBER,user.getLoginname());
                        session.setTimeout(3600000);
                        result = new Message().getResult(true,true,map.get("user"),null,null);
                    }else{
                        result = new Message().getResult(false,true,null,null,ConstantElement.securityUserError);
                    }
                }else{
                    result = new Message().getResult(false,true,null,null,ConstantElement.securityUserError);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
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


    @RequestMapping(value = ControllerNames.securityController_logout ,method = RequestMethod.POST)
	public @ResponseBody
    JSONObject logout() {
        SecurityUtils.getSubject().logout();
		return new Message().getResult(true,true,ConstantElement.securityUserLogoutSuccess,null,null);
	}
    @RequestMapping(value = ControllerNames.securityController_findUserInfo,method = RequestMethod.POST)
    public @ResponseBody  JSONObject findUserInfo() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        if(session.getAttribute(ConstantElement._USERINFO)==null){
            return new Message().getResult(true,true,ConstantElement.securityUserStatusError,null,null);
        }else{
            return new Message().getResult(true,true,session.getAttribute(ConstantElement._USERINFO),null,null);
        }
    }

    /**
     * 修改密码
     * @param oldpassword   原密码
     * @param newpassword   新密码
     * @return
     */
    @RequestMapping(value = ControllerNames.securityController_updatePwd,method = RequestMethod.POST)
    public @ResponseBody  JSONObject updatePwd(@RequestParam("oldpassword")String oldpassword,@RequestParam("newpassword")String newpassword) {
        Message msg=new Message();
        try {
            Subject subject = SecurityUtils.getSubject();
            User user=((User)subject.getSession(true).getAttribute(ConstantElement._USERINFO));
            userService.updatePassword(user.getId(),oldpassword,newpassword);
            return msg.getResult(true,true,ConstantElement.commonSuccess,null,null);
        }catch (ServiceException e) {
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,e.getMessage(),ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error(e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }


}
