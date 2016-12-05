package com.drink.service.impl;

import com.drink.cornerstone.constant.ConstantElement;
import com.drink.dao.ThreeGroupMapper;
import com.drink.dao.ThreeMemberMapper;
import com.drink.model.ThreeGroup;
import com.drink.module.Page;
import com.drink.module.ThreeGroupVo;
import com.drink.module.TreeNode;
import com.drink.service.ThreeGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by stq on 16-11-16.
 */
@Service("threeGroupService")
public class ThreeGroupServiceImpl implements ThreeGroupService {
    @Autowired
    private ThreeGroupMapper threeGroupMapper;
    @Autowired
    private ThreeMemberMapper memberMapper;
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
        int start = page.getCurrentNum();
        int end=page.getPageSize()> ConstantElement.pageSize?ConstantElement.pageSize:page.getPageSize();
        ThreeGroupVo vo = page.getObj();
        int totalsize=threeGroupMapper.findCountByCondition(vo);
        page.calculate(totalsize, start, end);
        vo.setStart(page.getStartPos());
        vo.setLimit(page.getEndPos());
        List<ThreeGroupVo> list=threeGroupMapper.findDataByCondition(vo);
        if(list!=null && list.size()>0){
            for(ThreeGroupVo tg:list){
                if(tg==null){continue;}
                tg.setMemberName(memberMapper.selectNameByGroupId(tg.getId()));
            }
        }
        page.setDatas(list);
        return page;
    }

    @Override
    public List<TreeNode> findStructurlByGroupId(Long groupId) {
        List<TreeNode> list = new ArrayList<>();
        recursionTreeNode(groupId,list);
        return list;
    }

    /**
     * 递归获取小组
     * @param groupId
     * @param list
     */
    private void recursionTreeNode(Long groupId, List<TreeNode> list) {
        List<ThreeGroup> groups = threeGroupMapper.getThreeNodeByGroupId(groupId);
        if(groups!=null && groups.size()>0){
            for(ThreeGroup group:groups){
                TreeNode node = new TreeNode();
                node.setId(group.getId());
                node.setName(group.getGroupName());
                node.setpId(group.getPid());
                list.add(node);
                if(group.getId()!=null){
                    recursionTreeNode(group.getId(),list);
                }
            }
        }
    }
}
