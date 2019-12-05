package com.sxp.patMag.enums;

public enum ScheduleEnum {
    DRL("待认领"),
    DTJ("待提交"),
    WTG("未通过"),
    BXZ("编写中"),
    YJS("维护中"),
    YDJ("已提交");

    private String name;

    ScheduleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
