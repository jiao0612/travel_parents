package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;

public interface SetMealService {
    PageResult findPages(QueryPageBean queryPageBean);

    Result add(Setmeal setmeal, Integer[] travelgroupIds);

}
