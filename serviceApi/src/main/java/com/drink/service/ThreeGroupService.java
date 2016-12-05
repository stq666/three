package com.drink.service;

import com.drink.model.ThreeGroup;
import com.drink.module.Page;
import com.drink.module.ThreeGroupVo;
import com.drink.module.TreeNode;

import java.util.List;

/**
 * Created by stq on 16-11-16.
 */
public interface ThreeGroupService {
    /**
     * 保存抱团组
     * @param group
     */
    void saveThreeGroup(ThreeGroup group);

    /**
     * 分页展示组
     * @param page
     * @return
     */
    Page<ThreeGroupVo> findPageThreeGroupByCondition(Page<ThreeGroupVo> page);

    /**
     * 获取组织架构图
     * @param groupId
     * @return
     */
    List<TreeNode> findStructurlByGroupId(Long groupId);

}
