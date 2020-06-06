package com.lzh.wx.entity.token;

import lombok.Data;

@Data
public class AccessToken {

    private String accessToken;

    private Long expiresTime;


    public AccessToken(String accessToken, String expiresIn) {
        this.accessToken = accessToken;
        this.expiresTime = System.currentTimeMillis() + Integer.parseInt(expiresIn) * 1000;
    }

    /**
     * 判断是否过期
     *
     * @return
     */
    public boolean isExpores() {
        return System.currentTimeMillis() > this.expiresTime;
    }


}
