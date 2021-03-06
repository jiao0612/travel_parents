package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;

import java.util.Map;

public interface TravelGroupService {

    PageResult findPages(QueryPageBean queryPageBean);

    void add(TravelGroup travelGroup, Integer[] travelItemIds);

    Result findPageById(Integer id);

    void update(TravelGroup travelGroup,Integer[] travelItemIds);

    Result findGroupAndItemByGroupId(Integer id);

    void delete(Integer id);

    PageResult findAll();

}
