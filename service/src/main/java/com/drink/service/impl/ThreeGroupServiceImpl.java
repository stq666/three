package com.drink.service.impl;

import com.drink.dao.ThreeGroupMapper;
import com.drink.model.ThreeGroup;
import com.drink.module.Page;
import com.drink.module.ThreeGroupVo;
import com.drink.service.ThreeGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by stq on 16-11-16.
 */
@Service("threeGroupService")
public class ThreeGroupServiceImpl implements ThreeGroupService {
    @Autowired
    private ThreeGroupMapper threeGroupMapper;
    @Override
    public void saveThreeGroup(ThreeGroup group) {
        //获取最大的顺序
        int maxSort = threeGroupMapper.getMaxSort();
        group.setGroupSort(maxSort++);
        group.setCreateTime(new Date());
        threeGroupMapper.insert(group);
    }

    @Override
    public Page<ThreeGroupVo> findPageThreeGroupByCondition(Page<ThreeGroupVo> page) {
        return null;
    }
}
