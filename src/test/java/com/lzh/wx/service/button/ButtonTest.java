package com.lzh.wx.service.button;


import com.alibaba.fastjson.JSONObject;
import com.lzh.wx.entity.menu.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ButtonTest {

    @Test
    public void testButton() {
        Button button = new Button();
        //菜单一
        button.getButton().add(new ClickButton("一级菜单", "1"));
        //菜单二
        button.getButton().add(new ViewButton("一级跳转", "http://www.baidu.com"));
        //菜单三
        SubButton sb = new SubButton("有子菜单");
        sb.getSub_button().add(new PhotoOrAlbumButton("传图", "31"));
        sb.getSub_button().add(new ClickButton("点击", "32"));
        sb.getSub_button().add(new ViewButton("网易新闻", "http://news.163.com"));
        button.getButton().add(sb);
        System.out.println(JSONObject.toJSONString(button));

    }
}
