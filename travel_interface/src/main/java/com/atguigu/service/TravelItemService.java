package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelItem;

import java.awt.*;

public interface TravelItemService {

    /**
    *@Author dell
    *@Date travelItem
    *@returnType: void
    *@Description: 增加自由行
    */
    void add(TravelItem travelItem);

    /**
    *@Author dell
    *@Date queryPageBean
    *@returnType: PageResult
    *@Description: 查询自由行数据
    */
    PageResult selectByPage(QueryPageBean queryPageBean);

    /**
     *@Author dell
     *@Date :row.id
     *@returnType:result
     *@Description:根据row.id删除自由行
     */
    void deleteByPrimaryKey(Integer id);

    /**
    *@Author dell
    *@Date
    *@returnType:void
    *@Description:更新自由行数据
    */
    void updateByPrimary(TravelItem travelItem);


    PageResult findAll();
}
