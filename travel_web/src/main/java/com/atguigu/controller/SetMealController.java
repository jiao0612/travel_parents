package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetMealService;
import com.atguigu.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    private SetMealService setMealService;

    @Autowired
    private JedisPool jedisPool;

    /*上传图片到七牛云*/
    @RequestMapping("/upload")
    public Result uploadImgFileToQiNuiServer(@RequestParam("imgFile") MultipartFile imgFile) {
        try {
            /*设置文件名*/
            String newFileName = UUID.randomUUID().toString()+imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf("."));
            /*获取上传文件的字节*/
            byte[] bytes = imgFile.getBytes();
            /*执行上传操作*/
            QiniuUtils.upload2Qiniu(bytes,newFileName);
            /*返回结果，包含上传文件名，回显图片*/
            try {
                jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,newFileName);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
            }
            return new Result(true, MessageConstant.UPLOAD_SUCCESS,newFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping(value = "/findPages", method = RequestMethod.POST)
    public PageResult findPages(@RequestBody QueryPageBean queryPageBean) {
        return setMealService.findPages(queryPageBean);
    }

    @RequestMapping(value = "/findPagesById", method = RequestMethod.GET)
    public Result findPagesById(Integer id){
        return setMealService.findPagesById(id);
    }

    @RequestMapping(value = "/findArrByMealId",method = RequestMethod.GET)
    public Result findArrByMealId(Integer id){
        try {
            List<Integer> list = setMealService.findArrByMealId(id);
            return  new Result(true, MessageConstant.QUERY_MEALANDGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_MEALANDGROUP_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('SETMEAL_ADD')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Setmeal setmeal , Integer[] travelgroupIds) {
        /*增加套餐信息*/
        try {
            setMealService.add(setmeal,travelgroupIds);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @PreAuthorize("hasAuthority('SETMEAL_DELETE')")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Result delete(Integer id){
        try {
            setMealService.delete(id);
            return new Result(true, MessageConstant.DELETE_MEAL_SUCCESS);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return new Result(false, MessageConstant.DELETE_RELATIONSHIP_ERROR);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_MEAL_FILE);
        }
    }

    @PreAuthorize("hasAuthority('SETMEAL_EDIT')")
    @RequestMapping(value = "/updateMealById", method = RequestMethod.POST)
    public Result updateMealById(@RequestBody Setmeal setmeal,Integer[] travelgroupIds){
        try {
            setMealService.updateMealById(setmeal,travelgroupIds);
            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCEESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FILE);
        }
    }

}
