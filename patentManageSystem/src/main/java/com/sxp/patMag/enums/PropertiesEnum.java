package com.sxp.patMag.enums;

public enum PropertiesEnum {
    HISTORY("History", "历史记录"),
    INDICATOR("Indicator", "指标"),
    REMARKVIEW("RemarkView", "专利的备注状态"),
    NOTICE("Notice", "通知"),
    PATENT("Patent", "专利"),
    JBOOK("Jbook", "交底书");
    private String englishName;
    private String chineseName;

    PropertiesEnum(String englishName, String chineseName) {
        this.englishName = englishName;
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }
}
