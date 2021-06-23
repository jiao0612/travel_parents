package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisConstant;
import com.atguigu.dao.SetmealMapper;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetMealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    @Qualifier("setmealMapper")
    private SetmealMapper setmealMapper;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public PageResult findPages(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page page = setmealMapper.selectAll(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(Setmeal setmeal, Integer[] travelgroupIds) {
        setmealMapper.insert(setmeal);//主键回显
        setMealAndGroupByMealId(setmeal.getId(),travelgroupIds);//设置关联数据
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());//图片名称存储到redis集合
    }

    @Override
    public Result findPagesById(Integer id) {
        Result result = null;
        try {
            Setmeal setmeal = setmealMapper.selectByPrimaryKey(id);
            result = new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
        return result;
    }

    @Override
    public void updateMealById(Setmeal setmeal ,Integer[] travelgroupIds) {
        setmealMapper.updateByPrimaryKey(setmeal);
        /*删除原有的mealid和group数据，再去新增*/
        deleteMealAndGroupByMealId(setmeal.getId());
        setMealAndGroupByMealId(setmeal.getId(), travelgroupIds);
    }

    @Override
    public List<Integer> findArrByMealId(Integer id) {
        return setmealMapper.selectMealAndGroupByMealId(id);
    }

    @Override
    public void delete(Integer id) {
        deleteMealAndGroupByMealId(id);
        /*在删除meal数据*/
        setmealMapper.deleteByPrimaryKey(id);
    }

    private void setMealAndGroupByMealId(Integer setMealId, Integer[] travelgroupIds) {
        /*根据mealid添加mealgroup数据*/
        if (travelgroupIds != null && travelgroupIds.length > 0){
            for (Integer travelGroupId : travelgroupIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("setMealId", setMealId);
                map.put("travelGroupId", travelGroupId);
                setmealMapper.setMealAndGroupByMealId(map);
            }
        }
    }
    private void deleteMealAndGroupByMealId(Integer setMealId) {
        try {
            setmealMapper.deleteMealAndGroupByMealId(setMealId);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
