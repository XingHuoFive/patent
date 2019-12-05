package com.sxp.patMag.enums;

/**
 * @author： Jude
 * @date:2019/11/26
 * @time:11:21
 */
public  enum ActionEnum {

    SELECT("查询"),
    UPDATE("修改"),
    LOGIN("用户登录"),
    INSERT("新增"),
    CHECK("审核"),
    SUBMIT("提交"),
    UPLOAD("上传文件"),
    EXPORT("导出"),

    SELECT1("select"),
    SELECT2("get"),
    SELECT3("list"),
    SELECT4("List"),
    SELECT5("show"),


    UPDATE1("update"),
    LOGIN1("login"),
    INSERT1("add"),
    INSERT2("insert"),
    INSERT3("list"),

    CHECK1("check"),
    SUBMIT1("submit"),
    UPLOAD1("upload"),
    EXPORT1("export"),
    READ1("read");

    // 成员变量
    /**
     * name
     */
    private String name;
    // 构造方法
    private ActionEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}