package com.lzh.wx.entity.template;

import lombok.Data;

@Data
public class Template {

    private String touser;

    private String template_id;

    private String url;

    private Miniprogram miniprogram;

    private com.lzh.wx.entity.template.Data data;
}
