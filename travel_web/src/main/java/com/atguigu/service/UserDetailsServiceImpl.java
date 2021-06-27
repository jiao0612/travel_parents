package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.pojo.Permission;
import com.atguigu.pojo.Role;
import com.atguigu.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根据名称获取用户信息
        User user = userService.selectByUserName(username);
        //如果用户为空，说明没有该用户信息，需要注册，登录错误
        if (user == null){
            return null;
        }
        //如果存在用户，取出该用户的权限信息
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                //存储权限
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        //返回用户名，密码，权限集合
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), list);
        return userDetails;
    }
}
