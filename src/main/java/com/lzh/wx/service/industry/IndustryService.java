package com.lzh.wx.service.industry;

import com.alibaba.fastjson.JSONObject;
import com.lzh.wx.service.token.TokenService;
import com.lzh.wx.utils.OkHttpUtil;
import okhttp3.Response;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class IndustryService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenService tokenService;

    public void setIndustry() {
        String accessToken = tokenService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=".concat(accessToken);
        JSONObject param = new JSONObject();
        param.put("industry_id1", "1");
        param.put("industry_id2", "4");
        try {
            String data = OkHttpUtil.getInstance().postJson(url, JSONObject.toJSONString(param));
            logger.info(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getIndustry() {
        String accessToken = tokenService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=".concat(accessToken);
        Response data = OkHttpUtil.getInstance().getData(url);
        try {
            String industrys = data.body().string();
            logger.info(industrys);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
