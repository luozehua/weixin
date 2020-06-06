package com.lzh.wx.constant;


public enum MsgType {

    TEXT("text"), IMAGE("image"), VOICE("voice"), VIDEO("video"), MUSIC("music"), NEWS("news");

    String type;

    MsgType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
