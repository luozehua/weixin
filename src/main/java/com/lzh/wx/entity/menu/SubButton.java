package com.lzh.wx.entity.menu;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubButton extends AbstractButton {

    List<AbstractButton> sub_button = new ArrayList<>();

    public SubButton(String name) {
        super(name);
    }
}
