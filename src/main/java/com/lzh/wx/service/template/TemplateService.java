package com.lzh.wx.service.template;

import com.alibaba.fastjson.JSONObject;
import com.lzh.wx.entity.template.Data;
import com.lzh.wx.entity.template.Template;
import com.lzh.wx.service.token.TokenService;
import com.lzh.wx.utils.OkHttpUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class TemplateService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenService tokenService;

    private static final String OPENID = "oCzps0a4IRfCzoa_1fWaZwubAcmE";

    private static final String TEMPLATEID = "WUQ8tI43TFtbUUpB6T_jjQTOGAtgm9djepfqpZabrV8";

    public void sendTemplateMsg() {
        String accessToken = tokenService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=".concat(accessToken);

        Template template = new Template();
        template.setTouser(OPENID);
        template.setTemplate_id(TEMPLATEID);
        Data data = new Data();
        Map<String, String> first = new HashMap<>();
        first.put("value", "您好!您投递的简历有新的反馈");
        first.put("color", "#173177");
        data.setFirst(first);

        Map<String, String> company = new HashMap<>();
        company.put("value", "北京58同城信息技术有限公司");
        company.put("color", "#173177");
        data.setCompany(company);

        Map<String, String> time = new HashMap<>();
        time.put("value", "2014-06-24");
        time.put("color", "#173177");
        data.setTime(time);

        Map<String, String> result = new HashMap<>();
        result.put("value", "已提交上级部门");
        result.put("color", "#173177");
        data.setResult(result);

        Map<String, String> remark = new HashMap<>();
        remark.put("value", "请耐心等待");
        remark.put("color", "#173177");
        data.setRemark(remark);
        template.setData(data);

        String paramJson = JSONObject.toJSONString(template);
        System.out.println(paramJson);
        try {
            String r = OkHttpUtil.getInstance().postJson(url, paramJson);
            logger.info(r);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
