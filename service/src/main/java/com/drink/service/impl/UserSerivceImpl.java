package com.drink.service.impl;

import com.drink.cornerstone.util.MD5;
import com.drink.dao.SysUserMapper;
import com.drink.model.SysUser;
import com.drink.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/5/1 0001.
 */
@Service("userService")
public class UserSerivceImpl implements UserService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public SysUser findAllByCondition(SysUser user) {
        return sysUserMapper.selectAllByCondition(user);
    }
}
