package com.lzh.wx.entity.message;

import com.lzh.wx.constant.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.Map;

@Data
@XStreamAlias("xml")
public class NewsMsg extends BaseMsg {

    @XStreamAlias("ArticleCount")
    private String articleCount;
    
    @XStreamAlias("Articles")
    private Articles articles;

    public NewsMsg(Map<String, String> requestMap) {
        super(requestMap);
        this.setMsgType(MsgType.NEWS.getType());
    }
}
