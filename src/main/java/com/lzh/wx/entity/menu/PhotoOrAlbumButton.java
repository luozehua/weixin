package com.lzh.wx.entity.menu;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PhotoOrAlbumButton extends  AbstractButton {

    private String type = "pic_photo_or_album";

    private String key;

    private List<AbstractButton> sub_button = new ArrayList<>();

    public PhotoOrAlbumButton(String name, String key) {
        super(name);
        this.key = key;
    }
}
