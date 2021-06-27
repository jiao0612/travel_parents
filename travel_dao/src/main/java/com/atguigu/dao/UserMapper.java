package com.atguigu.dao;

import com.atguigu.pojo.Menu;
import com.atguigu.pojo.User;
import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User selectByUserName(String username);

}