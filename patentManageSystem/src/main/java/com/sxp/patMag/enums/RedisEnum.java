package com.sxp.patMag.enums;

/**
 * @author： Jude
 * @date:2019/11/26
 * @time:11:21
 */
public  enum RedisEnum {

    LOGKEY("logs") ;
    // 成员变量
    /**
     * name
     */
    private String name;
    // 构造方法
    private RedisEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}