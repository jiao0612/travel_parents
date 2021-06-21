package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/travelGroup")
public class TravelGroupController {

    @Reference
    private TravelGroupService travelGroupService;

    @RequestMapping(value = "/findPages",method = RequestMethod.POST)
    public PageResult findPages( @RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = travelGroupService.findPages(queryPageBean);
        return pageResult;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestBody TravelGroup travelGroup ,Integer[] travelItemIds ){
        Result result =  travelGroupService.add(travelGroup,travelItemIds);
        return result;
    }

    @RequestMapping(value = "/findPageById",method = RequestMethod.GET)
    public Result findPageById(Integer id){
        Result result = travelGroupService.findPageById(id);
        return result;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody TravelGroup travelGroup, Integer[] travelItemIds){
        Result result = travelGroupService.update(travelGroup,travelItemIds);
        return  result;
    }

    @RequestMapping(value = "/findGroupAndItemByGroupId",method = RequestMethod.GET)
    public Result findGroupAndItemByGroupId(Integer id){
        Result result = travelGroupService.findGroupAndItemByGroupId(id);
        return result;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Result delete(Integer id){
        Result result = travelGroupService.delete(id);
        return result;
    }

}
