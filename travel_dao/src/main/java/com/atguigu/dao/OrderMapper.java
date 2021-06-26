package com.atguigu.dao;

import com.atguigu.pojo.Order;
import org.apache.ibatis.annotations.MapKey;
import org.aspectj.weaver.ast.Var;

import java.util.List;
import java.util.Map;

public interface OrderMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    //未指定map的key
    Map selectByPrimaryKey(Integer id);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);

    List<Order> findByCondition(Order order);
}