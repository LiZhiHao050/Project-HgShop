package com.lizhihao.hgshop.service.impl;

import com.lizhihao.hgshop.dao.IndexMapper;
import com.lizhihao.hgshop.service.IndexService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author LZH
 * @date 2019/12/25
 * Describe: 登录服务实现层
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    IndexMapper indexMapper;

    /**
     * 登录
     * @param user
     * @param pwd
     * @return
     */
    @Override
    public boolean login(String user, String pwd) {
        int login = indexMapper.login(user, pwd);
        System.out.println("登录服务层:" + login);
        return login > 0;
    }
}
