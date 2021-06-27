package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/travelItem")
public class TravelItemController {

    @Reference
    private TravelItemService travelItemService;

    @RequestMapping(value = "/selectPages",method = RequestMethod.POST)
    public PageResult select( @RequestBody QueryPageBean queryPageBean){
        return travelItemService.selectByPage(queryPageBean);
    }

    @RequestMapping(value = "/findPages",method = RequestMethod.POST)
    public PageResult findPages(){
        return travelItemService.findAll();
    }

    @PreAuthorize("hasAuthority('TRAVELITEM_ADD')")//权限校验
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result response(@RequestBody TravelItem travelItem){

        try {
            travelItemService.add(travelItem);
            return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('TRAVELITEM_DELETE')")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result delete(@RequestBody TravelItem row){
        try {
            travelItemService.deleteByPrimaryKey(row.getId());
            return new Result(true, MessageConstant.DELETE_TRAVELITEM_SUCCESS);
        }catch (RuntimeException ex){
            ex.printStackTrace();
            return new Result(false,ex.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('TRAVELITEM_EDIT')")
    @RequestMapping(value = "/edit",method =RequestMethod.POST)
    public Result update(@RequestBody TravelItem travelItem){
        try {
            travelItemService.updateByPrimary(travelItem);
            /*更新成功*/
            return new Result(true, MessageConstant.EDIT_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            /*更新失败*/
            return new Result(false, MessageConstant.EDIT_TRAVELITEM_FAIL);
        }
    }
}
