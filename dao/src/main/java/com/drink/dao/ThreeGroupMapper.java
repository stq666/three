package com.drink.dao;

import com.drink.daogen.ThreeGroupMapperGen;
import com.drink.model.ThreeGroup;
import com.drink.module.ThreeGroupVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ThreeGroupMapper extends ThreeGroupMapperGen {
    /**
     * 获取最大的排序
     * @return
     */
    int getMaxSort();

    /**
     * 获取刚插入的主键
     * @return
     */
    long selectId();

    /**
     * 通过主键修改父id
     * @param id
     * @param pid
     */
    void updatePidById(@Param("id") Long id, @Param("pid") Long pid);

    /**
     * 获取pid
     * @param id
     * @return
     */
    Long getPidByGroupId(@Param("id") Long id);

    int findCountByCondition(ThreeGroupVo vo);

    List<ThreeGroupVo> findDataByCondition(ThreeGroupVo vo);

    List<ThreeGroup> getThreeNodeByGroupId(Long groupId);
}