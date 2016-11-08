package com.drink.dao;

import com.drink.daogen.PublicRowMapperGen;
import com.drink.model.PublicRow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PublicRowMapper extends PublicRowMapperGen {

    void insertPublicRow(PublicRow pr);

    /**
     * 获取自己的所有介绍人的编号
     * @param pserialnumber
     * @return
     */
    List<String> findSubSerialNumber(@Param("pserialnumber")String pserialnumber);
}