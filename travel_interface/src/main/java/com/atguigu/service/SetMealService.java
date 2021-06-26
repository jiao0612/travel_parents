package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;

import java.util.List;

public interface SetMealService {

    PageResult findPages(QueryPageBean queryPageBean);

    void add(Setmeal setmeal,Integer[] travelgroupIds);

    Result findPagesById(Integer id);

    void updateMealById(Setmeal setmeal,Integer[] travelgroupIds);

    List<Integer> findArrByMealId(Integer id);

    void delete(Integer id);




}
