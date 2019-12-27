package com.lizhihao.hgshop.service.impl;

import com.google.gson.Gson;
import com.lizhihao.hgshop.pojo.Category;
import com.lizhihao.hgshop.service.RedisCategoryService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author LZH
 * @date 2019/12/25
 * Describe: 分类Redis服务实现层
 */

@Service
public class RedisCategoryServiceImpl implements RedisCategoryService {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;


    /**
     * 新增
     * @param category
     */
    @Override
    public void addCategory(Category category) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        // 获取Json对象
        Gson gson = new Gson();
        // 转换为Json字符串
        String json = gson.toJson(category);
        // 存储到Redis中
        values.set("add", json);

        System.out.println("Redis新增:" + json);

        System.out.println("商品分类" + category.getId() + "已新增");

        // kafka发送字符串
        kafkaTemplate.sendDefault("add", json);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @Override
    public void delCategory(String ids) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        // 将要删除的id存入Redis
        values.set("del", ids);

        System.out.println("Redis删除:" + ids);
        System.out.println("商品分类" + ids + "已删除");

        // Kafka发送id
        kafkaTemplate.sendDefault("del",ids);

    }

    /**
     * 修改
     * @param category
     */
    @Override
    public void modCategory(Category category) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        // 获取Json对象
        Gson gson = new Gson();
        // 转换为Json字符串
        String json = gson.toJson(category);
        // 存储到Redis中
        values.set("mod", json);

        System.out.println("Redis修改:" + json);
        System.out.println("商品分类" + category.getId() + "已修改");

        // kafka发送字符串
        kafkaTemplate.sendDefault("mod", json);
    }

}
