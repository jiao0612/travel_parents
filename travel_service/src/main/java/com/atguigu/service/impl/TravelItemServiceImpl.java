package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.TravelItemMapper;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = TravelItemService.class)
@Transactional
public class TravelItemServiceImpl implements TravelItemService {

    @Autowired
    @Qualifier("travelItemMapper")
    private TravelItemMapper travelItemMapper;

    @Override
    public Result add(TravelItem travelItem) {
        Result result = null;
        /*增加自由行记录*/
        int insert = 0;
        try {
            insert = travelItemMapper.insert(travelItem);
            result = new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            result = new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public PageResult selectByPage(QueryPageBean queryPageBean) {
        /*开启分页*/
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        /*获取查询结果*/
        Page<TravelItem> travelItems = travelItemMapper.selectAll(queryPageBean.getQueryString());//传参查询条件
        /*封装并返回*/
        return new PageResult(travelItems.getTotal(),travelItems.getResult());
    }

    @Override
    public Result deleteByPrimaryKey(Integer id) {
        Result result = null;
        try {
            travelItemMapper.deleteByPrimaryKey(id);
            result = new Result(true, MessageConstant.DELETE_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            result = new Result(false, MessageConstant.DELETE_TRAVELITEM_FAIL);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result updateByPrimary(TravelItem travelItem) {
        Result result = null;
        try {
            travelItemMapper.updateByPrimaryKey(travelItem);
            /*更新成功*/
            result = new Result(true, MessageConstant.EDIT_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            /*更新失败*/
            result = new Result(false, MessageConstant.EDIT_TRAVELITEM_FAIL);
            e.printStackTrace();
        }
        return result;
    }
}
