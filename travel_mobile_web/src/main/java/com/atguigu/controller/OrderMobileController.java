package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Order;
import com.atguigu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/Order")
public class OrderMobileController {

    @Reference
   private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping(value = "/addOrderInfo",method = RequestMethod.POST)
    public Result addOrderInfo(@RequestBody Map map){
        //获取手机号
        String telephone = (String) map.get("telephone");
        //获取验证码
        String validateCode = (String) map.get("validateCode");
        //从redis中获取验证码进行比对
        String key = telephone + RedisMessageConstant.SENDTYPE_ORDER;
        String code_redis = jedisPool.getResource().get(key);
        if (!validateCode.equals(code_redis)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Result result =null;
        try {
            //对预约信息进行保存
         map.put("orderType", Order.ORDERTYPE_TELEPHONE);
         result = orderService.order(map);
            return new Result(true, MessageConstant.ORDER_SUCCESS,result);
        }catch (RuntimeException ex){
            ex.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FULL);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL);
        }
    }

    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    public Result findById(Integer id){

        try {
            Map map = orderService.findById(id);
            return new Result(true, MessageConstant.ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL);
        }

    }
}
