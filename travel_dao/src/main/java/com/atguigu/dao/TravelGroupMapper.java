package com.atguigu.dao;

import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface TravelGroupMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TravelGroup record);

    TravelGroup selectByPrimaryKey(Integer id);

    Page selectAll(String queryString);

    int updateByPrimaryKey(TravelGroup record);

    void setTravelGroupAndTravelItem(Map<String, Integer> map);

    List<Integer> findGroupAndItemByGroupId(Integer id);

    void deleteTravelGroupAndItemByTravelItemId(Integer id);

    List<TravelGroup> findTravelGroupsById(Integer id);
}