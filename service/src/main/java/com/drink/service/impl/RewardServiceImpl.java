package com.drink.service.impl;

import com.drink.cornerstone.constant.ConstantElement;
import com.drink.dao.ThreeRewardMapper;
import com.drink.module.Page;
import com.drink.module.ThreeMemberVo;
import com.drink.module.ThreeRewardVo;
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
    private ThreeRewardMapper threeRewardMapper;
    @Override
    public Page<ThreeRewardVo> findPageThreeRewardByCondition(Page<ThreeRewardVo> page) {
        int start = page.getCurrentNum();
        int end=page.getPageSize()> ConstantElement.pageSize?ConstantElement.pageSize:page.getPageSize();
        ThreeRewardVo vo = page.getObj();
        int totalsize=threeRewardMapper.findCountByCondition(vo);
        page.calculate(totalsize, start, end);
        vo.setStart(page.getStartPos());
        vo.setLimit(page.getEndPos());
        List<ThreeRewardVo> list=threeRewardMapper.findDataByCondition(vo);
        page.setDatas(list);
        return page;
    }
}
