package com.atguigu.service;

import com.atguigu.entity.Result;
import com.atguigu.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void add(List<OrderSetting> orderSettings);

    List<Map<String, Integer>> findTableList(String orderDate);

    void setNumByOrderDate(OrderSetting orderSetting);
}
