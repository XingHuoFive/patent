package com.sxp.patMag.enums;

/**
 * @author： Jude
 * @date:2019/11/26
 * @time:11:21
 */
public  enum ProcessEnum {

    NEW("新建专利"),
    CLAIM("专利认领"),
    CHECK("审核"),
    UPDATE("修改专利"),
    USERLOGIN("UserLogin:"),
    SUBMIT("提交"),
    SUBMITPATENT("提交专利"),
    UPLOADFILES("上传文件"),
    UPLOADJBOOK("上传交底书"),
    UPLOAD("上传"),
    FIRSTCHECK("初审"),
    SECONDCHECK("复审"),
    FIRSTCHECKREJECTED("初审驳回"),
    SECONDCHECKREJECTED("复审驳回"),
    FIRSTCHECKPASS("初审通过"),
    SECONDCHECKPASS("复审通过");
    // 成员变量
    /**
     * name
     */
    private String name;
    // 构造方法
    private ProcessEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}