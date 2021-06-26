package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.SetmealMapper;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetMealMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service(interfaceClass = SetMealMobileService.class)
@Transactional
public class SetMealMobileServiceImpl implements SetMealMobileService {

    @Autowired
    private SetmealMapper setmealMapper;

    /*--------------手机网页端-----------------------*/
    @Override
    public List<Setmeal> findAllPages() {
        List<Setmeal> setMeals = setmealMapper.findAllPages();
        return setMeals;
    }

    @Override
    public Setmeal findById(Integer id) {
        Setmeal setmeal = setmealMapper.selectByPrimaryKey(id);//根据套餐id查询套餐数据,包括travelGroups
        return setmeal;
    }

    @Override
    public Setmeal findSthById(Integer id) {
        Setmeal setmeal = setmealMapper.selectByPrimaryKey(id);
        return setmeal;
    }
}
