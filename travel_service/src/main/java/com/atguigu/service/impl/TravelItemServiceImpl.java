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
    public void add(TravelItem travelItem) {
        /*增加自由行记录*/
        travelItemMapper.insert(travelItem);
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
    public void deleteByPrimaryKey(Integer id) {
        //根据自由行id查询关联表的数据，如果存在关联，抛出异常，无法删除。
        int count = travelItemMapper.findTravelGroupAndTravelItemByTravelItemId(id);
        if(count>0){ //说明存在关联数据
            throw new RuntimeException(MessageConstant.DELETE_RELATIONSHIP_ERROR);
        }
        travelItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateByPrimary(TravelItem travelItem) {
        travelItemMapper.updateByPrimaryKey(travelItem);
    }

    @Override
    public PageResult findAll() {
        Page<TravelItem> travelItems = travelItemMapper.selectAll(null);
        return new PageResult(travelItems.getTotal(), travelItems.getResult());
    }
}
