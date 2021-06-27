package com.atguigu.service;

import com.atguigu.pojo.Menu;
import com.atguigu.pojo.User;

import java.util.List;

public interface UserService {
    User selectByUserName(String username);
}
