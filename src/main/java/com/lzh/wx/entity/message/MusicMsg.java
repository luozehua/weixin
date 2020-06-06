package com.lzh.wx.entity.message;

import com.lzh.wx.constant.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.Map;

@Data
@XStreamAlias("xml")
public class MusicMsg extends BaseMsg {

    @XStreamAlias("Music")
    private Music music;

    public MusicMsg(Map<String, String> requestMap) {
        super(requestMap);
        this.setMsgType(MsgType.MUSIC.getType());
    }
}
