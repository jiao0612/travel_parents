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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
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
        Result result = null;
        try {
            /*设置文件名*/
            String newFileName = UUID.randomUUID().toString()+imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf("."));
            /*获取上传文件的字节*/
            byte[] bytes = imgFile.getBytes();
            /*执行上传操作*/
            QiniuUtils.upload2Qiniu(bytes,newFileName);
            /*返回结果，包含上传文件名，回显图片*/
            result = new Result(true, MessageConstant.UPLOAD_SUCCESS,newFileName);
            try {
                jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,newFileName);
            } catch (Exception e) {
                result = new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return result;
    }

    @RequestMapping(value = "/findPages", method = RequestMethod.POST)
    public PageResult findPages(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = setMealService.findPages(queryPageBean);
        return pageResult;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Setmeal setmeal , Integer[] travelgroupIds) {
        /*增加套餐信息*/
        Result result = setMealService.add(setmeal,travelgroupIds);

        return result;
    }

}
