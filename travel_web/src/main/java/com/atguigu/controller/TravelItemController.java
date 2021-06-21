package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/travelItem")
public class TravelItemController {

    @Reference
    private TravelItemService travelItemService;

    /**
    *@Author dell
    *@Date travelitem
    *@returnType:result
    *@Description: 增加自由行数据
    */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result response(@RequestBody TravelItem travelItem){
        /*增加自由行记录*/
        try {
            travelItemService.add(travelItem);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
    }
    /**
    *@Author dell
    *@Date ：所要查询的页数，pageNum
    *@returnType:json
    *@Description:异步显示自由行的分页数据
    */
    @RequestMapping(value = "/selectPages",method = RequestMethod.POST)
    public PageResult select( @RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = travelItemService.selectByPage(queryPageBean);
        return pageResult;
    }
    @RequestMapping(value = "/findPages",method = RequestMethod.POST)
    public PageResult findPages(){
        PageResult pageResult = travelItemService.findAll();
        return pageResult;
    }

    /**
    *@Author dell
    *@Date :row
    *@returnType:result
    *@Description:根据row删除自由行
    */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result delete(@RequestBody TravelItem row){
        Result result = travelItemService.deleteByPrimaryKey(row.getId());
        return result;
    }

    /**
    *@Author dell
    *@Date :travelitem
    *@returnType:result
    *@Description: 更新自由行数据
    */
    @RequestMapping(value = "/edit",method =RequestMethod.POST)
    public Result update(@RequestBody TravelItem travelItem){
        Result result = travelItemService.updateByPrimary(travelItem);
        return result;
    }
}
