package com.lzh.wx.service.menu;

import com.alibaba.fastjson.JSONObject;
import com.lzh.wx.entity.menu.*;
import com.lzh.wx.service.token.TokenService;
import com.lzh.wx.utils.OkHttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MenuService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenService tokenService;

    /**
     * 创建菜单
     */
    public void createMenu() {
        Button button = new Button();
        //菜单一
        button.getButton().add(new ClickButton("一级菜单", "1"));
        //菜单二
        button.getButton().add(new ViewButton("一级跳转", "http://15e702i402.51mypc.cn/index.htm"));
        //菜单三
        SubButton sb = new SubButton("有子菜单");
        sb.getSub_button().add(new PhotoOrAlbumButton("传图", "31"));
        sb.getSub_button().add(new ClickButton("点击", "32"));
        sb.getSub_button().add(new ViewButton("网易新闻", "http://news.163.com"));
        button.getButton().add(sb);

        String accessToken = tokenService.getAccessToken();

        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=".concat(accessToken);

        try {
            String result = OkHttpUtil.getInstance().postJson(url, JSONObject.toJSONString(button));
            logger.info("自定义菜单：" + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
