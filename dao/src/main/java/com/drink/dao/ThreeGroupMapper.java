package com.drink.dao;

import com.drink.daogen.ThreeGroupMapperGen;

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
}