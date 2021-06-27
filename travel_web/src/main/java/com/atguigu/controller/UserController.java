package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Menu;
import com.atguigu.pojo.Role;
import com.atguigu.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;


    @RequestMapping(value = "/getUsername",method = RequestMethod.GET)
    public Result getUsername(){
        try{
            //框架过滤器会将session域的用户信息取出，通过ThreadLocal绑定到当前线程上。
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            com.atguigu.pojo.User user01 = userService.selectByUserName(user.getUsername());
          /*  Set<Role> roles = user01.getRoles();
            //根据角色，拉取菜单
            List<Object> list = new ArrayList<>();
            for (Role role : roles) {
                LinkedHashSet<Menu> menus = role.getMenus();
                list.add(menus);
            }*/
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user01);
        }catch (Exception e){
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    @RequestMapping(value = "/getMenuByUserName",method = RequestMethod.GET)
    public Result getMenuByUserName(String username){
        try {
            com.atguigu.pojo.User user = userService.selectByUserName(username);
            Set<Role> roles = user.getRoles();
            //根据角色，拉取菜单
            List<Object> list = new ArrayList<>();
            for (Role role : roles) {
                LinkedHashSet<Menu> menus = role.getMenus();
                list.add(menus);
            }
            return new Result(true, MessageConstant.GET_MENU_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MENU_FAIL);
        }


    }
}
