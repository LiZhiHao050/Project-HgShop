package com.lizhihao.hgshop.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.lizhihao.hgshop.pojo.Category;
import com.lizhihao.hgshop.service.CategoryRedisService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author LZH
 * @date 2019/12/23
 * Describe: 分类Redis服务实现类
 */
@Service
public class CategoryRedisServiceImpl implements CategoryRedisService {

    @Autowired
    RedisTemplate<String, String> redisTemplate;


    @Override
    public PageInfo<Category> list(Category category, Integer pageNum) {
        Integer pageSize = 4;
        Gson gson = new Gson();
        //1.分页查询分类id列表
        List<String> ids = redisTemplate.opsForList().range("ITEM-CAT-LIST", (pageNum-1) * pageSize, pageNum * pageSize - 1);
        System.err.println("---------------------" + ids + "----------------------");
        //2.需要从hash中找到各个id的详细信息
//		List<Category> list = new ArrayList<>();
//		for (String id : ids) {
//			String json = (String) redisTemplate.opsForHash().get("ITEM-CAT-HASH", id);
//			list.add(gson.fromJson(json, Category.class));
//		}
        List<Category> list = ids.stream().map((Function<? super String, ? extends Category>) id -> {
            String json = (String) redisTemplate.opsForHash().get("ITEM-CAT-HASH", id);
            return gson.fromJson(json, Category.class);
        }).collect(Collectors.toList());
        //3.模糊查询
        if (category.getName() != null) {
            list = list.stream().filter(c -> c.getName().contains(category.getName())).collect(Collectors.toList());
        }

        System.err.println("CList: " + list);

        //4.填充PageInfo，用于页面分页展示
        PageInfo<Category> pageInfo = new PageInfo<>(list);
        //4.1.总记录数
        long total = redisTemplate.opsForList().size("ITEM-CAT-LIST");
        pageInfo.setTotal(total);
        //4.2.总页数
        pageInfo.setPages((int)(total%pageSize==0 ? total/pageSize : total/pageSize + 1));
        //4.3.[1,2,3,4,...]
        int[] navigatepageNums = new int[pageInfo.getPages()];
        for (int i = 1; i <= pageInfo.getPages(); i++) {
            navigatepageNums[i-1] = i;
        }
        pageInfo.setNavigatepageNums(navigatepageNums);
        //4.4.当前页
        pageInfo.setPageNum(pageNum);

        return pageInfo;
    }

    /**
     * 获取分类
     * @return
     */
    @Override
    public List<Category> getAllCategories() {
        Gson gson = new Gson();
        Set<String> members = redisTemplate.opsForSet().members("ITEM-CAT-SET::0");
        List<Category> childs = members.stream().map((Function<? super String, ? extends Category>) m1 -> {
            String json = (String) redisTemplate.opsForHash().get("ITEM-CAT-HASH", m1);
            return gson.fromJson(json, Category.class);
        }).collect(Collectors.toList());

        childs.forEach(c1 -> {
            Set<String> members2 = redisTemplate.opsForSet().members("ITEM-CAT-SET::" + c1.getId());
            List<Category> childs2 = members2.stream().map((Function<? super String, ? extends Category>) m2 -> {
                String json2 = (String) redisTemplate.opsForHash().get("ITEM-CAT-HASH", m2);
                return gson.fromJson(json2, Category.class);
            }).collect(Collectors.toList());
            c1.setChilds(childs2);
        });

        return childs;
    }

    /**
     * 添加分类
     * @param category
     * @param type
     */
    @Override
    public void addCategory(Category category, Integer type) {
        Gson gson = new Gson();
        if (type == 1) {
            //1.添加
            //1.1.往list类型中添加分类id
            redisTemplate.opsForList().leftPush("ITEM-CAT-LIST", category.getId()+"");
        } else {
            //2.修改
            //修改set中维护的父子关系
            //2.1.查询旧数据
            String json = (String)redisTemplate.opsForHash().get("ITEM-CAT-HASH", category.getId()+"");
            Category oldCategory = gson.fromJson(json, Category.class);
            //2.2.set中删除子分类id
            redisTemplate.opsForSet().remove("ITEM-CAT-SET::" + oldCategory.getParentId(), category.getId()+"");


            Set<String> ids = redisTemplate.opsForSet().members("ITEM-CAT-SET::" + category.getId());
            System.err.println("*********************" + ids + "*******************");
            ids.forEach(id -> {
                String json1 = (String) redisTemplate.opsForHash().get("ITEM-CAT-HASH", id);
                Category c = gson.fromJson(json1, Category.class);
                c.setParentName(category.getName());
                json1 = gson.toJson(c);
                System.err.println("22222222222222222222222222222222");
                System.err.println(json1);
                redisTemplate.opsForHash().put("ITEM-CAT-HASH", id, json1);
            });
        }
        //3.添加和修改共用的代码
        //3.1.hash中存放分类整体数据
        String json = gson.toJson(category);
        redisTemplate.opsForHash().put("ITEM-CAT-HASH", category.getId()+"", json);
        //3.2.set中维护父子关系  key:父分类id value:分类id
        redisTemplate.opsForSet().add("ITEM-CAT-SET::" + category.getParentId(), category.getId()+"");
    }


    /**
     * 通过ID获取分类
     * @param id
     * @return
     */
    @Override
    public Category getCategoryById(Integer id) {
        Gson gson = new Gson();
        //通过分类id查询hash中的分类详情
        String json = (String)redisTemplate.opsForHash().get("ITEM-CAT-HASH", id+"");
        Category category = gson.fromJson(json, Category.class);
        return category;
    }

    /**
     * 删除分类
     * @param id
     */
    @Override
    public void deleteCategory(Integer id) {
        Long count = redisTemplate.opsForSet().size("ITEM-CAT-SET::" + id);
        if (count > 0) {
            return;
        }
        Gson gson = new Gson();
        String json = (String)redisTemplate.opsForHash().get("ITEM-CAT-HASH", id + "");
        Category category = gson.fromJson(json, Category.class);
        redisTemplate.opsForSet().remove("ITEM-CAT-SET::" + category.getParentId(), id + "");
        redisTemplate.delete("ITEM-CAT-SET::" + id);
        redisTemplate.opsForList().remove("ITEM-CAT-LIST", 1, id + "");
        redisTemplate.opsForHash().delete("ITEM-CAT-HASH", id + "");
    }
}
