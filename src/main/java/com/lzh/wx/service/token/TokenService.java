package com.lzh.wx.service.token;

import com.alibaba.fastjson.JSONObject;
import com.lzh.wx.utils.OkHttpUtil;
import com.lzh.wx.utils.RedisUtil;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class TokenService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${wx.app.id}")
    private String appId;

    @Value("${wx.app.secret}")
    private String appSecret;

    @Value("${baidu.app.key}")
    private String baiduAppKey;

    @Value("${baidu.app.secret}")
    private String baiduAppSecret;

    @Resource
    private RedisUtil redisUtil;


    private static final String ACCESS_TOKEN = "access_token";
    private static final String EXPIRES_IN = "expires_in";

    private static final String BD_ACCESS_TOKEN = "baidu_access_token";

    // private static AccessToken accessToken;


//    private void getToken() {
//        String Url = String.format("hhttps://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", this.appId, this.appSecret);
//        Response data = OkHttpUtil.getInstance().getData(Url);
//        try {
//            String string = data.body().string();
//            accessToken = new AccessToken("", "");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 获取AccessToken
//     * @return
//     */
//    public String getAccessToken() {
//        if (accessToken == null || accessToken.isExpores()) {
//            getToken();
//        }
//        return accessToken.getAccessToken();
//    }


    /**
     * 获取AccessToken，寸入redis中
     */
    public String getAccessToken() {
        Object tokenObject = redisUtil.get(ACCESS_TOKEN);
        String accessToken = null;
        if (tokenObject == null) {
            String Url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", this.appId, this.appSecret);
            Response data = OkHttpUtil.getInstance().getData(Url);
            try {
                String tokenJsonStr = data.body().string();
                JSONObject tokenJson = (JSONObject) JSONObject.parse(tokenJsonStr);
                accessToken = tokenJson.getString(ACCESS_TOKEN);
                Integer expiresIn = tokenJson.getInteger(EXPIRES_IN);
                redisUtil.set(ACCESS_TOKEN, accessToken, expiresIn);
                logger.info("重新获取AccessToken");
            } catch (IOException e) {
                logger.error("获取日志异常：" + e.getMessage());
                e.printStackTrace();
            }
        } else {
            accessToken = tokenObject.toString();
        }
        return accessToken;
    }


    /**
     * 获取百度的AccessToken
     *
     * @return
     */
    public String getBaiduAccessToken() {
        Object tokenObject = redisUtil.get(BD_ACCESS_TOKEN);
        String accessToken = null;
        if (tokenObject == null) {
            String url = String.format("https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=%s&client_secret=%s", this.baiduAppKey, this.baiduAppSecret);
            Response data = OkHttpUtil.getInstance().getData(url);
            try {
                String tokenJsonStr = data.body().string();
                JSONObject tokenJson = (JSONObject) JSONObject.parse(tokenJsonStr);
                accessToken = tokenJson.getString(ACCESS_TOKEN);
                Integer expiresIn = tokenJson.getInteger(EXPIRES_IN);
                redisUtil.set(BD_ACCESS_TOKEN, accessToken, expiresIn);
                logger.info("重新获取AccessToken");
            } catch (IOException e) {
                logger.error("获取日志异常：" + e.getMessage());
                e.printStackTrace();
            }
        } else {
            accessToken = tokenObject.toString();
        }
        return accessToken;
    }
}
