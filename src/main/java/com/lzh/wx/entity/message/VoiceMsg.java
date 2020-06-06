package com.lzh.wx.entity.message;

import com.lzh.wx.constant.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.Map;

@Data
@XStreamAlias("xml")
public class VoiceMsg extends BaseMsg {

    @XStreamAlias("Voice")
    private Voice voice;

    public VoiceMsg(Map<String, String> requestMap) {
        super(requestMap);
        this.setMsgType(MsgType.VOICE.getType());
    }
}
