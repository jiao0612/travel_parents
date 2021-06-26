package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping(value = "/getValidateCode")
    public Result getValidateCode(String phoneNum){
        try {
            Integer integer = ValidateCodeUtils.generateValidateCode(4);//获取验证码
         /*   SMSUtils.sendShortMessage(phoneNum, String.valueOf(integer));//给手机发验证码*/
            jedisPool.getResource().setex(phoneNum + RedisMessageConstant.SENDTYPE_ORDER, 300,String.valueOf(integer));//存入redis集合5分钟
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }


}
