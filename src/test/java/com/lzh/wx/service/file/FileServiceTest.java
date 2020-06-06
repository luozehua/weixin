package com.lzh.wx.service.file;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@RunWith(SpringRunner.class)
class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Test
    void upload() {
        String path = "C:\\a.png";
        String image = fileService.upload(path, "image");
        System.out.println(image);
    }

    @Test
    void upload2() {
        String path = "C:\\a.png";
        String image = null;
        try {
            image = fileService.uploadV2(path, "image");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(image);
    }
}