package com.lzh.wx.entity.template;

import java.util.Map;

@lombok.Data
public class Data {

    private Map<String, String> first;
    private Map<String, String> company;
    private Map<String, String> time;
    private Map<String, String> result;
    private Map<String, String> remark;
}
