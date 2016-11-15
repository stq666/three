package com.drink.dao;

import com.drink.daogen.ThreeMemberMapperGen;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ThreeMemberMapper extends ThreeMemberMapperGen {
    /**
     * 根据组查找成员
     * @param groupId
     * @return
     */
    List<Long> selectIdsByGroupId(@Param("groupId") Long groupId);
}