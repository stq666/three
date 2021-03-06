package com.drink.dao;

import com.drink.daogen.SysUserMapperGen;
import com.drink.model.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper extends SysUserMapperGen {
    /**
     * 根据传递的参数查询用户的基本信息（不关联其他表）
     * @param user  查询条件
     * @return
     */
    SysUser selectAllByCondition(SysUser user);

    void save(SysUser user);

    void updatePasswordById(@Param("id")Long id, @Param("password")String newpassword);

}