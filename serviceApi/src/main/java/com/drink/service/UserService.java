package com.drink.service;

import com.drink.model.SysUser;

/**
 * 用户的业务接口
 * Created by Administrator on 2016/5/1 0001.
 */
public interface UserService {
    /**
     * 根据条件查找用户的基本信息
     * @param user
     * @return
     */
    SysUser findAllByCondition(SysUser user);

}
