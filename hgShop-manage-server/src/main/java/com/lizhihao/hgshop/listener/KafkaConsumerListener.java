package com.lizhihao.hgshop.listener;

import com.google.gson.Gson;
import com.lizhihao.hgshop.dao.CategoryMapper;
import com.lizhihao.hgshop.pojo.Category;
import com.lizhihao.hgshop.service.CategoryRedisService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author LZH
 * @date 2019/12/23
 * Describe: Kafka消费者监听
 */

@Component
public class KafkaConsumerListener implements MessageListener<String, String> {

    @Autowired
    CategoryRedisService crService;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public void onMessage(ConsumerRecord<String, String> record) {
        // 获取Key值
        String key = record.key();
        if (key == null) {
            return;
        }

        Gson gson = new Gson();
        ValueOperations<String, String> values = redisTemplate.opsForValue();

        /**
         * 新增, 删除, 修改
         */
        if ("add".equals(key)) {
            // 得到Redis的key，取出该条记录
            String json = values.get("add");
            Category category = gson.fromJson(json, Category.class);
            // 利用该接口对象新增到MySQL数据库并保存成功
            categoryMapper.addCategory(category);
            // 删除Redis中的记录
            redisTemplate.delete("add");

            System.out.println("商品分类" + category.getId() + "已新增");
        } else if ("mod".equals(key)) {
            // 得到Redis的key，取出该条记录
            String json = values.get("mod");
            Category category = gson.fromJson(json, Category.class);
            // 利用该接口对象新增到MySQL数据库并保存成功
            categoryMapper.updCategory(category);
            // 删除Redis中的记录
            redisTemplate.delete("mod");

            System.out.println("商品分类" + category.getId() + "已修改");
        } else if ("del".equals(key)) {
            // 得到Redis的key，取出该条记录
            String ids = values.get("del");
            // 在Mysql中删除
            categoryMapper.delCategory(Integer.parseInt(ids));

            // 删除Redis中的记录
            redisTemplate.delete("del");

            System.out.println("商品分类" + ids + "已删除");
        }




        /*if ("1".equals(key) || "2".equals(key)) {
            // 获取值
            String json = record.value();
            // 字符串转换为对象
            Gson gson = new Gson();
            Category category = gson.fromJson(json, Category.class);
            // 添加或修改Mysql数据库
            crService.addCategory(category, Integer.parseInt(key));
            if ("1".equals("key")) {
                System.out.println("商品分类" + category.getId() + "已新增");
            } else {
                System.out.println("商品分类" + category.getId() + "已修改");
            }
        } else {
            int id = Integer.parseInt(record.value());
            // 调用方法删除Mysql中的数据
            crService.deleteCategory(id);
            System.out.println("商品分类" + id + "已新增");
        }*/

    }

}
