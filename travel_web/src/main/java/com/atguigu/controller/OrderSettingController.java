package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderSettingService;
import com.atguigu.util.POIUtils;
import org.aspectj.weaver.ast.Var;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping(value = "/findTableList",method = RequestMethod.GET)
    public Result findTableList(String orderDate){
        try {
            List<Map<String,Integer>> list = orderSettingService.findTableList(orderDate);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('ORDERSETTING')")
    @RequestMapping(value = "/upload")
    public Result upload(@RequestBody MultipartFile multipartFile){
        try {
            //解析文件获取list集合
            List<String[]> strings = POIUtils.readExcel(multipartFile);
            List<OrderSetting> orderSettings = new ArrayList<OrderSetting>();
            //循环遍历每一行数据
            for (String[] str : strings) {
                //封装为一个ordersetting
                OrderSetting orderSetting = new OrderSetting(new Date(str[0]), Integer.parseInt(str[1]));
                orderSettings.add(orderSetting);
            }
           orderSettingService.add(orderSettings);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('ORDERSETTING')")
    @RequestMapping(value = "/setNumByOrderDate",method = RequestMethod.POST)
    public Result setNumByOrderDate(@RequestBody OrderSetting orderSetting){

        try {
            orderSettingService.setNumByOrderDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
