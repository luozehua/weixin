package com.lzh.wx.service.industry;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class IndustryServiceTest {

    @Autowired
    private IndustryService industryService;


    @Test
    void setIndustry() {
        industryService.setIndustry();
    }

    @Test
    void getIndustry() {
        industryService.getIndustry();
    }
}