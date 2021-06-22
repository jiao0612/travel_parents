package com.atguigu.dao;

import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SetmealMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_setmeal
     *
     * @mbg.generated Thu Jun 17 17:20:34 SGT 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_setmeal
     *
     * @mbg.generated Thu Jun 17 17:20:34 SGT 2021
     */
    int insert(Setmeal record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_setmeal
     *
     * @mbg.generated Thu Jun 17 17:20:34 SGT 2021
     */
    Setmeal selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_setmeal
     *
     * @mbg.generated Thu Jun 17 17:20:34 SGT 2021
     */
    Page selectAll(String queryString);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_setmeal
     *
     * @mbg.generated Thu Jun 17 17:20:34 SGT 2021
     */
    int updateByPrimaryKey(Setmeal record);

    void setMealAndGroupByMealId(Map map);
}