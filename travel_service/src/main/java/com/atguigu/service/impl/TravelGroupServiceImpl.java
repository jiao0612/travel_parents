package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.TravelGroupMapper;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {

    @Autowired
    private TravelGroupMapper travelGroupMapper;

    @Override
    public PageResult findPages(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page page = travelGroupMapper.selectAll(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Result add(TravelGroup travelGroup, Integer[] travelItemIds) {
        Result result = null;
        try {
            travelGroupMapper.insert(travelGroup);//返回结果id
            /*封装参数，添加表连接数据*/
            setTravelGroupAndItemByTravelItemId(travelGroup.getId(), travelItemIds);
            result = new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            result = new Result(false, MessageConstant.ADD_TRAVELGROUP_FAIL);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result findPageById(Integer id) {
        Result result = null;
        try {
            TravelGroup travelGroup = travelGroupMapper.selectByPrimaryKey(id);
            result = new Result(true, MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroup);
        } catch (Exception e) {
            result = new Result(false, MessageConstant.QUERY_TRAVELGROUP_FAIL);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result update(TravelGroup travelGroup,Integer[] travelItemIds) {
        Result result = null;
        try {
            travelGroupMapper.updateByPrimaryKey(travelGroup);//编辑跟团游
            travelGroupMapper.deleteTravelGroupAndItemByTravelItemId(travelGroup.getId());//删除外键表列
            setTravelGroupAndItemByTravelItemId(travelGroup.getId(), travelItemIds);//编辑自由行

            result = new Result(true, MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            result = new Result(false, MessageConstant.EDIT_TRAVELGROUP_FAIL);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result findGroupAndItemByGroupId(Integer id) {
        Result result = null;
        try {
            List<Integer> list = travelGroupMapper.findGroupAndItemByGroupId(id);
            result = new Result(true, MessageConstant.QUERY_GROUPANDITEM_LIST_SUCCESS,list);
        } catch (Exception e) {
            result = new Result(false, MessageConstant.QUERY_GROUPANDITEM_LIST_FAIL);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result delete(Integer id) {
        Result result =null;
        try {
            travelGroupMapper.deleteTravelGroupAndItemByTravelItemId(id);//删除外键表列
            travelGroupMapper.deleteByPrimaryKey(id);//删除主键表列

            result = new Result(true, MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            result = new Result(false, MessageConstant.DELETE_TRAVELGROUP_FAIL);
            e.printStackTrace();
        }
        return result;
    }

    private void setTravelGroupAndItemByTravelItemId(Integer travelGroupId, Integer[] travelItemIds) {
        if (travelItemIds != null && travelItemIds.length > 0){
            for (Integer travelItemId : travelItemIds) {
                // 如果有多个参数使用map
                Map<String, Integer> map = new HashMap<>();
                map.put("travelGroup",travelGroupId);
                map.put("travelItem",travelItemId);
                travelGroupMapper.setTravelGroupAndTravelItem(map);
            }
        }
    }
}
