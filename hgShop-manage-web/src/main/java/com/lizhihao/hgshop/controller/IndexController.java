package com.lizhihao.hgshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LZH
 * @date 2019/12/10
 * Describe:
 */

@Controller
public class IndexController {

    @RequestMapping("login")
    @ResponseBody
    public String login() {
        return "";
    }

    @RequestMapping("index")
    public String index() {
        return "index";
    }

}
