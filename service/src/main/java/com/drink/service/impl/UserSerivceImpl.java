package com.drink.service.impl;

import com.drink.cornerstone.constant.ConstantElement;
import com.drink.cornerstone.service.ServiceException;
import com.drink.cornerstone.util.MD5;
import com.drink.dao.MemberMapper;
import com.drink.dao.UserMapper;
import com.drink.model.User;
import com.drink.module.member.MemberVo;
import com.drink.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/1 0001.
 */
@Service("userService")
public class UserSerivceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    MemberMapper memberMapper;

    @Override
    public Map findUserByNameAndPwd(String loginName, String pwd) {
        Map map = new HashMap();
        User user = userMapper.selectByLoginNameAndPwd(loginName,new MD5().getMD5ofStr(pwd));
        MemberVo vo = null;
        if(user!=null){
            vo = memberMapper.selectById(user.getMemberid());
            map.put("member",vo);
        }
        map.put("user",user);
        return map;
    }

    @Override
    public User findUserByName(String loginName) {
        return userMapper.selectByLoginName(loginName);

    }

    @Override
    public void updatePassword(Long id, String oldpassword, String newpassword) {
        //首先查找原密码是否正确
        int count = userMapper.selectUserByIdAndPassword(id,new MD5().getMD5ofStr(oldpassword));
        if(count==0){//原密码不正确
            throw new ServiceException(ConstantElement.errorPassord);
        }
        userMapper.updatePasswordById(id,new MD5().getMD5ofStr(newpassword));
    }
}
