package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrderSettingMapper;
import com.atguigu.entity.Result;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderSettingService;
import com.atguigu.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Override
    public void add(List<OrderSetting> orderSettings) {
        if (orderSettings!=null && orderSettings.size()>0){
            for (OrderSetting orderSetting : orderSettings) {
                /*判断数据库中是否存在该日期*/
                Integer integer = selectOrderSettingByDate(orderSetting.getOrderDate());
                if (integer <= 0){
                    orderSettingMapper.insert(orderSetting);
                }else {
                    orderSettingMapper.updateNumberByOrderDate(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map<String, Integer>> findTableList(String orderDate) {
        /*根据月份查询所有数据*/
        String start = orderDate+"-01";
        //String end = orderDate+"-31";  //mysql高版本中，如果不存在31号的月份，安装这个日期进行查询时会报错。
        String end = DateUtils.getLastDayOfMonth(orderDate);
        Map<String,String> param = new HashMap<>();
        param.put("start",start);
        param.put("end",end);
        List<OrderSetting> list = orderSettingMapper.selectAllByOrderDate(param);

        List<Map<String, Integer>> result = new ArrayList<>();

        for (OrderSetting orderSetting : list) {
            Map<String, Integer> map = new HashMap<>();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            result.add(map);
        }
        return result;
    }

    @Override
    public void setNumByOrderDate(OrderSetting orderSetting) {
        /*判断该日期是否有预约信息*/
        Integer integer = orderSettingMapper.selectByOrderDate(orderSetting.getOrderDate());
        if (integer > 0){ //有该预约信息，修改 --> 若预约的消息中已经存有已预约，会不会清空？
            orderSettingMapper.updateNumberByOrderDate(orderSetting);
        }else {//没有，进行新增
            orderSettingMapper.insert(orderSetting);
        }
    }

    public Integer selectOrderSettingByDate(Date date){
       return orderSettingMapper.selectByOrderDate(date);
    }

}
