package com.atguigu.service;

import com.atguigu.entity.Result;
import com.atguigu.pojo.Order;

import java.util.Map;

public interface OrderService {

    /**
    *@Author dell
    *@Date
    *@returnType:void
    *@Description:
    */
    Result order(Map map) throws Exception;

    /**
    *@Author dell
    *@Date
    *@returnType:void
    *@Description:
     * @param id
    */
    Map findById(Integer id);

}
