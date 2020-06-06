package com.lzh.wx.service.menu;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Test
    void createMenu() {
        menuService.createMenu();
    }
}