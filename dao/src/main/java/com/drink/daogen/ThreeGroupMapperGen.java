package com.drink.daogen;

import com.drink.model.ThreeGroup;
import java.util.List;

public interface ThreeGroupMapperGen {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table three_group
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table three_group
     *
     * @mbggenerated
     */
    int insert(ThreeGroup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table three_group
     *
     * @mbggenerated
     */
    ThreeGroup selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table three_group
     *
     * @mbggenerated
     */
    List<ThreeGroup> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table three_group
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ThreeGroup record);
}