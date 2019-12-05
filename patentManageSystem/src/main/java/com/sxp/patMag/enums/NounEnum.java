package com.sxp.patMag.enums;

/**
 * @author： Jude
 * @date:2019/11/26
 * @time:11:21
 */
public  enum NounEnum {

    HISTORY("历史记录"),
    INDICATOR("指标"),
    REMARKVIEW("专利的备注状态"),
    NOTICE("通知"),
    PATENT("专利"),
    JBOOK("交底书"),

    HISTORY1("History"),
    INDICATOR1("Indicator"),
    REMARKVIEW1("RemarkView"),
    NOTICE1("Notice"),
    PATENT1("Patent"),
    JBOOK1("Jbook");


    // 成员变量
    /**
     * name
     */
    private String name;
    // 构造方法
    private NounEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}