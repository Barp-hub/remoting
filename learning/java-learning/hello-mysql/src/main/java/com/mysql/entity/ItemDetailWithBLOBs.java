package com.mysql.entity;

public class ItemDetailWithBLOBs extends ItemDetail {
    private String desc;

    private String intro;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}