package com.lzh.wx.entity.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("item")
public class Article {

    @XStreamAlias("Title")
    private String title;

    @XStreamAlias("Description")
    private String description;

    @XStreamAlias("PicUrl")
    private String picUrl;

    @XStreamAlias("Url")
    private String url;
}
