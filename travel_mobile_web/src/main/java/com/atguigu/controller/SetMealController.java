package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetMealMobileService;
import com.atguigu.service.SetMealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Setmeal")
public class SetMealController {

    @Reference
    private SetMealMobileService setMealMobileService;

    @RequestMapping(value = "/getSetmeal",method = RequestMethod.POST)
    public Result getSetMeal(){
        try {
            List<Setmeal> setMeals = setMealMobileService.findAllPages();
          return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setMeals);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public Result findById(Integer id){
        try {
            Setmeal setmeal = setMealMobileService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }


    @RequestMapping(value = "/findSthById",method = RequestMethod.GET)
    public Result findSthById(Integer id){
        try {
            Setmeal setmeal = setMealMobileService.findSthById(id);
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

}
