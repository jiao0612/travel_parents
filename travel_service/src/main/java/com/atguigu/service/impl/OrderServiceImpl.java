package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.MemberMapper;
import com.atguigu.dao.OrderMapper;
import com.atguigu.dao.OrderSettingMapper;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderService;
import com.atguigu.util.DateUtils;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderSettingMapper orderSettingMapper;
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Result order(Map map) throws Exception{
        // 根据map中的信息，用户进行预约操作

        //1.判断当前日期是否可以进行预约
        //获取日期
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        OrderSetting orderSetting = orderSettingMapper.selectOrderSettingByOrderDate(date);

        if (orderSetting == null || orderSetting.getNumber() == 0){ //判断当前日期是否有团信息
            //没有团
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }else { //可以预约，但要判断预约人数是否已满
            //获取可预约人数，已预约人数
            int number = orderSetting.getNumber();
            int reservations = orderSetting.getReservations();
            if (number <= reservations ){ //人数超出，无法在进行预约
                return new Result(false, MessageConstant.ORDER_FULL);
            }
        }
        //判断当前用户是否为会员
        String telephone = (String) map.get("telephone");//获取用户的电话号
        Member member = memberMapper.selectMemberByTelephone(telephone);//获取会员信息
        if (member != null){
            //是会员，在判断是否预约过
            Integer setmealId = Integer.parseInt((String) map.get("setmealId"));//获取套餐id setmealId
            Order order = new Order(member.getId(), date, null, null, setmealId);
            //获取订单信息
            List<Order> orders = orderMapper.findByCondition(order);
            if (orders != null && orders.size() > 0){
                //预约过，不可以在进行预约
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }else {
            //不是会员，进行注册
            member = new Member();
            //获取用户信息，注册会员
            member.setName((String)map.get("name"));
            member.setSex((String)map.get("sex"));
            member.setPhoneNumber((String)map.get("telephone"));
            member.setIdCard((String)map.get("idCard"));
            member.setRegTime(new Date()); // 会员注册时间，当前时间
            //增加到数据库
            memberMapper.insert(member);
        }
        //设置订单数据，后台可预约数量-1，预约数量+1
        orderSetting.setNumber(orderSetting.getNumber()-1); // 最大预约数量-1，numbers
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingMapper.updateReservationsByOrderSetting(orderSetting);  // 可预约数量+1

        //设置预约信息，提示预约成功
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(date);
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setOrderType(Order.ORDERTYPE_TELEPHONE);
        Integer setmealId = Integer.parseInt((String) map.get("setmealId"));//获取套餐id setmealId
        order.setSetmealId(setmealId);
        orderMapper.insert(order);
        return new Result(true, MessageConstant.ORDER_SUCCESS,order);









       /* //检查当前日期是否进行了预约设置
        String orderDate = (String) map.get("orderDate");
        // 因为数据库预约设置表里面的时间是date类型，http协议传递的是字符串类型，所以需要转换
        Date date = DateUtils.parseString2Date(orderDate);
        // 使用预约时间查询预约设置表，看看是否可以 进行预约
        //（1）使用预约时间，查询预约设置表，判断是否有该记录
        OrderSetting orderSetting = orderSettingMapper.selectOrderSettingByOrderDate(date);
        // 如果预约设置表等于null，说明不能进行预约，压根就没有开团
        if (orderSetting == null || orderSetting.getNumber() == 0) {
            // 如果没有说明预约设置表没有进行设置，此时不能预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }else{
            //如果有，说明预约可以进行预约，//可预约人数
            int number = orderSetting.getNumber();
            //已预约人数
            int reservations = orderSetting.getReservations();
            //如果预约人数大于等于最大预约数，此时不能预约，提示“预约已满”
            if(reservations>=number){
                return new Result(false, MessageConstant.ORDER_FULL);
            }
        }
        //获取手机号码
        String telephone = (String) map.get("telephone");
        Member member = memberMapper.selectMemberByTelephone(telephone);//是否为会员
        if (member != null){ //是会员
            Integer memberId = member.getId();//获取会员id
            int setmealId = Integer.parseInt((String) map.get("setmealId"));//获取套餐id
            //创建订单，查询是否预约过
            Order order = new Order(memberId, date, null, null, setmealId);
            List<Order> orders = orderMapper.findByCondition(order);
            if (orders !=null && orders.size() > 0){ // 有预约信息，不能在进行预约
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }else { //不是会员，新增会员
            //获取会员信息
            member = new Member();
            member.setName((String)map.get("name"));
            member.setSex((String)map.get("sex"));
            member.setPhoneNumber((String)map.get("telephone"));
            member.setIdCard((String)map.get("idCard"));
            member.setRegTime(new Date()); // 会员注册时间，当前时间
            memberMapper.insert(member);
        }

        //对预约人数进行修改
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingMapper.updateReservationsByOrderDate(orderSetting);

        //（4）可以进行预约，向预约表中添加1条数据
        //保存预约信息到预约表
        Order order = new Order();
        order.setMemberId(member.getId()); //会员id
        order.setOrderDate(date); // 预约时间
        order.setOrderStatus(Order.ORDERSTATUS_NO); // 预约状态（已出游/未出游）
        order.setOrderType((String)map.get("orderType"));
        order.setSetmealId(Integer.parseInt((String)map.get("setmealId")));
        orderMapper.insert(order);

        return new Result(true, MessageConstant.ORDER_SUCCESS, order);*/
    }

    @Override
    public Map findById(Integer id) {
        //设置日期格式
        Map map = orderMapper.selectByPrimaryKey(id);
        Date orderDate = (Date) map.get("orderDate");
        try {
            map.put("orderDate", DateUtils.parseDate2String(orderDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
