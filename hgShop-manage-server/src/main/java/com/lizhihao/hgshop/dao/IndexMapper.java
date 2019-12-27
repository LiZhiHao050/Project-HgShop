package com.lizhihao.hgshop.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author LZH
 * @date 2019/12/25
 * Describe: 登录持久层
 */
public interface IndexMapper {

    // 登录
    @Select("SELECT COUNT(*) FROM hg_user WHERE username = #{user} and password = #{pwd}")
    public int login(@Param("user") String user, @Param("pwd") String pwd);

}
