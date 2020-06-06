package com.lzh.wx.service.token;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Test
    void getAccessToken() {
        String accessToken = tokenService.getAccessToken();
        System.out.println(accessToken);
    }


    @Test
    void getBaiduAccessToken(){
        String accessToken = tokenService.getBaiduAccessToken();
        System.out.println(accessToken);
    }
}