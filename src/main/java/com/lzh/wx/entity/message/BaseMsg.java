package com.lzh.wx.entity.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.Map;

@Data
public class BaseMsg {

    @XStreamAlias("ToUserName")
    private String toUserName;

    @XStreamAlias("FromUserName")
    private String fromUserName;

    @XStreamAlias("CreateTime")
    private String createTime;

    @XStreamAlias("MsgType")
    private String msgType;

    public BaseMsg(Map<String, String> requestMap) {
        this.toUserName = requestMap.get("FromUserName");
        this.fromUserName = requestMap.get("ToUserName");
        this.createTime = String.valueOf(System.currentTimeMillis() / 100);
    }

    @Override
    public String toString() {
        return "BaseMsg{" +
                "toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", msgType='" + msgType + '\'' +
                '}';
    }
}
