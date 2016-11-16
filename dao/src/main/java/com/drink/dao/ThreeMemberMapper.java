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

    /**
     * 根据组获取组成员的名称连接串
     * @param groupId
     * @return
     */
    String selectNameByGroupId(@Param("groupId") Long groupId);
}