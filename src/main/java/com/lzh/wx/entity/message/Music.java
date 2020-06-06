package com.lzh.wx.entity.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
public class Music {

    @XStreamAlias("Title")
    private String title;

    @XStreamAlias("Description")
    private String description;

    @XStreamAlias("MusicUrl")
    private String musicUrl;

    @XStreamAlias("HQMusicUrl")
    private String hQMusicUrl;

    @XStreamAlias("ThumbMediaId")
    private String thumbMediaId;
}
