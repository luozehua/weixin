package com.lzh.wx.entity.message;

import com.lzh.wx.constant.MsgType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.Map;


@Data
@XStreamAlias("xml")
public class ImageMsg extends BaseMsg {

    @XStreamAlias("Image")
    private Image image;

    public ImageMsg(Map<String, String> requestMap) {
        super(requestMap);
        this.setMsgType(MsgType.IMAGE.getType());
    }
}
