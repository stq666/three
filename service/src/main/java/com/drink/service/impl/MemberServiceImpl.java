package com.drink.service.impl;

import com.drink.service.MemberService;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/5/2 0002.
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService {
    Log log= LogFactory.getLog(MemberServiceImpl.class);

}
