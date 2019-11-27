package com.sxp.patMag.enums;

/**
 * @author： Jude
 * @date:2019/11/26
 * @time:11:21
 */


public  enum ProcessEnum {

    NEW("新建专利"), CLAIM("专利认领"), CHECK("审核"), UPDATE("修改字段");
    // 成员变量
    private String name;
    // 构造方法
    private ProcessEnum(String name){
        this.name = name;
    }


}