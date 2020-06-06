package com.lzh.wx.entity.message;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Articles {

    @XStreamImplicit
    private List<Article> articles;
}
