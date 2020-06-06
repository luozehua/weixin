package com.lzh.wx.entity.message;

import com.lzh.wx.constant.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.Map;

@Data
@XStreamAlias("xml")
public class TextMsg extends BaseMsg {

    @XStreamAlias("Content")
    private String content;

    public TextMsg(Map<String, String> requestMap) {
        super(requestMap);
        this.setMsgType(MsgType.TEXT.getType());
    }

    @Override
    public String toString() {
        return "TextMsg{" +
                "content='" + content + '\'' +
                "} " + super.toString();
    }
}
