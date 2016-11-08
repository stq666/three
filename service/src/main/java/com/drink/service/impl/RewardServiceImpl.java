package com.drink.service.impl;

import com.drink.cornerstone.constant.ConstantElement;
import com.drink.dao.RewardMapper;
import com.drink.module.Page;
import com.drink.module.member.RewordVo;
import com.drink.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/5/14 0014.
 */
@Service("rewardService")
public class RewardServiceImpl implements RewardService {
    @Autowired
    RewardMapper rewardMapper;
    @Override
    public Page<RewordVo> findPageMoneyByCondition(Page<RewordVo> page) {

        int start = page.getCurrentNum();
        int end=page.getPageSize()> ConstantElement.pageSize?ConstantElement.pageSize:page.getPageSize();
        RewordVo vo=page.getObj();
        int totalsize=rewardMapper.findCountByCondition(vo);
        page.calculate(totalsize, start, end);
        vo.setStart(page.getStartPos());
        vo.setLimit(page.getEndPos());
        List<RewordVo> list=rewardMapper.findDataByCondition(vo);

        page.setDatas(list);
        return page;
    }
}
