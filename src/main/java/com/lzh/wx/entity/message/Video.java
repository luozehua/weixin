package com.lzh.wx.entity.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
public class Video {
    @XStreamAlias("MediaId")
    private String mediaId;

    @XStreamAlias("Title")
    private String title;
    
    @XStreamAlias("Description")
    private String description;
}
