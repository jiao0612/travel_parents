package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.SetmealMapper;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetMealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    @Qualifier("setmealMapper")
    private SetmealMapper setmealMapper;

    @Override
    public PageResult findPages(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page page = setmealMapper.selectAll(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Result add(Setmeal setmeal, Integer[] travelgroupIds) {
        Result result = null;
        try {
            setmealMapper.insert(setmeal);//主键回显
            setMealAndGroupByMealId(setmeal.getId(),travelgroupIds);
            result = new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            result=  new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
            e.printStackTrace();
        }
        return result;
    }

    private void setMealAndGroupByMealId(Integer setMealId, Integer[] travelgroupIds) {

        /*根据mealid添加mealgroup数据*/
        if (travelgroupIds != null && travelgroupIds.length > 0){

            for (Integer travelGroupId : travelgroupIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("setMealId", setMealId);
                map.put("travelGroupId", travelGroupId);
                setmealMapper.setMealAndGroupByMealId(map);
            }
        }


    }

}
