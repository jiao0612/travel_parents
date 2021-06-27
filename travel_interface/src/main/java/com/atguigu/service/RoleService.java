package com.atguigu.service;

import com.atguigu.pojo.Menu;
import com.atguigu.pojo.Role;

import java.util.LinkedList;

public interface RoleService {
    LinkedList<Menu> getMenuByRoles(Role role);
}
