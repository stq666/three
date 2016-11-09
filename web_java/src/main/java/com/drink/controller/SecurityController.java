package com.drink.controller;


import com.alibaba.fastjson.JSONObject;
import com.drink.cornerstone.constant.BeanNames;
import com.drink.cornerstone.constant.ConstantElement;
import com.drink.cornerstone.constant.ControllerNames;
import com.drink.cornerstone.security.PasswordService;
import com.drink.cornerstone.service.ServiceException;
import com.drink.module.Message;
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


}
