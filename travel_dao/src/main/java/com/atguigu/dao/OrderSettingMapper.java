package com.atguigu.dao;

import com.atguigu.pojo.OrderSetting;
import com.atguigu.pojo.TravelGroup;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrderSetting record);

    OrderSetting selectByPrimaryKey(Integer id);

    List<OrderSetting> selectAll();

    int updateByPrimaryKey(OrderSetting record);

    Integer selectByOrderDate(Date date);

    void updateByOrderDate(OrderSetting orderSetting);

    void updateNumberByOrderDate(OrderSetting orderSetting);

    List<OrderSetting> selectAllByOrderDate(Map param);

    OrderSetting selectOrderSettingByOrderDate(Date date);

    void updateReservationsByOrderSetting(OrderSetting orderSetting);
}