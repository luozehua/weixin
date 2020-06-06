package com.lzh.wx.entity.message;

import com.lzh.wx.constant.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.Map;

@Data
@XStreamAlias("xml")
public class VideoMsg extends BaseMsg {
    @XStreamAlias("Video")
    private Video video;

    public VideoMsg(Map<String, String> requestMap) {
        super(requestMap);
        this.setMsgType(MsgType.VIDEO.getType());
    }
}
