package com.atguigu.service;

import com.atguigu.pojo.Setmeal;

import java.util.List;

public interface SetMealMobileService {

    List<Setmeal> findAllPages();

    Setmeal findById(Integer id);

    Setmeal findSthById(Integer id);
}
