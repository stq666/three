package com.drink.dao;

import com.drink.daogen.UserMapperGen;
import com.drink.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends UserMapperGen {
    /**
     * 根据用户名和密码获取用户
     * @param loginName
     * @param pwd
     * @return
     */
    public User selectByLoginNameAndPwd(@Param("loginName")String loginName, @Param("pwd")String pwd);

    /**
     * 根据用户名获取用户
     * @param loginName
     * @return
     */
    public User selectByLoginName(@Param("loginName")String loginName);

    /**
     * 查询原密码是否正确
     * @param id
     * @param md5ofStr
     * @return
     */
    public int selectUserByIdAndPassword(@Param("id")Long id, @Param("password")String md5ofStr);

    /**
     * 修改密码
     * @param id
     * @param newpassword
     */
    public void updatePasswordById(@Param("id")Long id, @Param("password")String newpassword);

    void insertUser(User user);
}