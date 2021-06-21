package com.atguigu.dao;

import com.atguigu.pojo.OrderSetting;
import java.util.List;

public interface OrderSettingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ordersetting
     *
     * @mbg.generated Thu Jun 17 17:20:34 SGT 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ordersetting
     *
     * @mbg.generated Thu Jun 17 17:20:34 SGT 2021
     */
    int insert(OrderSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ordersetting
     *
     * @mbg.generated Thu Jun 17 17:20:34 SGT 2021
     */
    OrderSetting selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ordersetting
     *
     * @mbg.generated Thu Jun 17 17:20:34 SGT 2021
     */
    List<OrderSetting> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ordersetting
     *
     * @mbg.generated Thu Jun 17 17:20:34 SGT 2021
     */
    int updateByPrimaryKey(OrderSetting record);
}