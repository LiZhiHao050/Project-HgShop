package com.lizhihao.hgshop.controller;

import com.lizhihao.hgshop.service.IndexService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LZH
 * @date 2019/12/10
 * Describe:
 */

@Controller
public class IndexController {

    @Reference
    IndexService indexService;

    /**
     * 登录
     * @param request
     * @param user
     * @param pwd
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public String login(HttpServletRequest request, String user, String pwd) {

        System.out.println("User:" + user);
        System.out.println("PWD:" + pwd);

        boolean login = indexService.login(user, pwd);

        if (login) {
            System.out.println("登录成功");
            return "redirect:index";
        } else {
            System.out.println("登录失败");
            return "login";
        }
    }

    @RequestMapping("index")
    public String index() {
        return "index";
    }

}
