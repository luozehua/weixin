package com.lzh.wx.auth;


import com.alibaba.fastjson.JSONObject;
import com.lzh.wx.service.auth.AuthService;
import com.lzh.wx.service.msg.MsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthService authService;

    @Autowired
    private MsgService msgService;

    @Value("${wx.token}")
    private String token;

    /**
     * 1）将token、timestamp、nonce三个参数进行字典序排序
     * 2）将三个参数字符串拼接成一个字符串进行sha1加密
     * 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */

    @GetMapping("auth")
    public String auth(String signature, String timestamp, String nonce, String echostr) {
        if (authService.check(signature, timestamp, nonce, token)) {
            return echostr;
        }
        return null;
    }

    /**
     * 当普通微信用户向公众账号发消息时，微信服务器将POST消息的XML数据包到开发者填写的URL上。
     * <p>
     * 请注意：
     * <p>
     * 关于重试的消息排重，推荐使用msgid排重。
     * 微信服务器在五秒内收不到响应会断掉连接，并且重新发起请求，总共重试三次。假如服务器无法保证在五秒内处理并回复，可以直接回复空串，微信服务器不会对此作任何处理，并且不会发起重试。详情请见“发送消息-被动回复消息”。
     * 如果开发者需要对用户消息在5秒内立即做出回应，即使用“发送消息-被动回复消息”接口向用户被动回复消息时，可以在公众平台官网的开发者中心处设置消息加密。开启加密后，用户发来的消息和开发者回复的消息都会被加密（但开发者通过客服接口等API调用形式向用户发送消息，则不受影响）。关于消息加解密的详细说明，请见“发送消息-被动回复消息加解密说明”。
     *
     * @param request
     * @throws IOException
     */
    @PostMapping("auth")
    public String msg(HttpServletRequest request) throws IOException {
        Map<String, String> result = msgService.getMsg(request.getInputStream());
        logger.info(JSONObject.toJSONString(result));
        return msgService.responseMsg(result);
    }


}
