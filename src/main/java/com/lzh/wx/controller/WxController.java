package com.lzh.wx.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WxController {

    @GetMapping("/num/{num}")
    public String getNum(@PathVariable("num") Integer num) {
        return "参数是：" + num * 10;
    }

}
