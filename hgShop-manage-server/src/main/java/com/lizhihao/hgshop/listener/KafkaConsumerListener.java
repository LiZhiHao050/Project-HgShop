package com.lizhihao.hgshop.listener;

import com.google.gson.Gson;
import com.lizhihao.hgshop.pojo.Category;
import com.lizhihao.hgshop.service.CategoryRedisService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author LZH
 * @date 2019/12/23
 * Describe:
 */
@Component
public class KafkaConsumerListener implements MessageListener<String, String> {

    @Autowired
    CategoryRedisService crService;

    @Override
    public void onMessage(ConsumerRecord<String, String> record) {
        // 获取Key值
        String key = record.key();
        if (key == null) {
            return;
        }

        if ("1".equals(key) || "2".equals(key)) {
            String json = record.value();
            Gson gson = new Gson();
            Category category = gson.fromJson(json, Category.class);
            crService.addCategory(category, Integer.parseInt(key));
        } else {
            int id = Integer.parseInt(record.value());
            crService.deleteCategory(id);
        }

    }

}
