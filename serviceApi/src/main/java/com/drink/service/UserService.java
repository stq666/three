package com.drink.service;

import com.drink.model.User;
import com.drink.module.member.MemberVo;

import java.util.Map;

/**
 * 用户的业务接口
 * Created by Administrator on 2016/5/1 0001.
 */
public interface UserService {
    /**
     * 根据密码和用户名获取员工信息
     * @param loginName
     * @param pwd
     * @return
     */
    public Map findUserByNameAndPwd(String loginName, String pwd);

    /**
     * 根据用户名查找用户
     * @param loginName
     * @return
     */
    public User findUserByName(String loginName);

    /**
     * 修改密码
     * @param id                当前登录用户主键
     * @param oldpassword       原密码
     * @param newpassword       新密码
     */
    public void updatePassword(Long id, String oldpassword, String newpassword);
}
