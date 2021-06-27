package com.atguigu.dao;

import com.atguigu.pojo.Menu;

import java.util.LinkedHashSet;
import java.util.List;

public interface MenuMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    Menu selectByPrimaryKey(Integer id);

    List<Menu> selectAll();

    int updateByPrimaryKey(Menu record);

    LinkedHashSet<Menu> selectAllById(Integer id);

    List<Menu> selectChildrenByParentMenuId(Integer id);

}