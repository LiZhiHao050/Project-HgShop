package com.lizhihao.hgshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LZH
 * @date 2019/12/10
 * Describe:
 */

@Controller
public class IndexController {

    @RequestMapping("login")
    public String login() {
        return "login";
    }

    @RequestMapping("index")
    public String index() {
        return "index";
    }

}
