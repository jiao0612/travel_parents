package com.atguigu.dao;

import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;

import java.util.List;

public interface TravelItemMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TravelItem record);

    TravelItem selectByPrimaryKey(Integer id);

    Page<TravelItem> selectAll(String queryString);

    int updateByPrimaryKey(TravelItem record);

    int findTravelGroupAndTravelItemByTravelItemId(Integer id);

    List<TravelItem> findAllByTravelGroupId(Integer  id);
}