package com.lzh.wx.service.auth;


import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AuthService {

    /**
     * 1）将token、timestamp、nonce三个参数进行字典序排序
     * 2）将三个参数字符串拼接成一个字符串进行sha1加密
     * 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param token
     * @return
     */
    public boolean check(String signature, String timestamp, String nonce, String token) {
        // 1）将token、timestamp、nonce三个参数进行字典序排序
        List<String> list = Arrays.asList(token, timestamp, nonce);
        Collections.sort(list);
        // 2）将三个参数字符串拼接成一个字符串进行sha1加密
        String concat = list.get(0).concat(list.get(1)).concat(list.get(2));
        String tempSign = sha1(concat);
        // 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        return signature.equals(tempSign);
    }

    // 加密算法
    private String sha1(String concat) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("sha1");
            byte[] digest = md.digest(concat.getBytes());
            String[] hexArray = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
            //处理
            for (byte b : digest) {
                //处理前4位 //与上15 的含义是去掉符号   &00001111
                sb.append(hexArray[b >> 4 & 15]);
                //处理后四位
                sb.append(hexArray[b & 15]);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
