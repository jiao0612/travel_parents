package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import com.github.pagehelper.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
        return travelGroupService.findPages(queryPageBean);
    }

    @RequestMapping(value = "/findPageById",method = RequestMethod.GET)
    public Result findPageById(Integer id){
        return travelGroupService.findPageById(id);
    }

    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    public PageResult findAll(){
        return travelGroupService.findAll();
    }

    @RequestMapping(value = "/findGroupAndItemByGroupId",method = RequestMethod.GET)
    public Result findGroupAndItemByGroupId(Integer id){
        return travelGroupService.findGroupAndItemByGroupId(id);
    }

    @PreAuthorize("hasAuthority('TRAVELGROUP_ADD')")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestBody TravelGroup travelGroup ,Integer[] travelItemIds ){
        try {
            travelGroupService.add(travelGroup,travelItemIds);
            return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TRAVELGROUP_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('TRAVELGROUP_EDIT')")
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Result delete(Integer id){
        try {
            travelGroupService.delete(id);
            return new Result(true, MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_TRAVELGROUP_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('TRAVELGROUP_EDIT')")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody TravelGroup travelGroup, Integer[] travelItemIds){
        try {
            travelGroupService.update(travelGroup,travelItemIds);
            return new Result(true, MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_TRAVELGROUP_FAIL);
        }
    }
}
