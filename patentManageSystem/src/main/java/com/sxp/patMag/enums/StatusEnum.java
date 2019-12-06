package com.sxp.patMag.enums;


/**
 * @author 硕
 * @date
 */
public enum StatusEnum {

    Success(0,"成功"),
    Fail(1,"无匹配专利"),
    FAIL(1, "失败"),
    CHECKYES(0, "审核通过"),
    CHECKNO(1, "审核不通过"),
    Objeck_NULL(1,"对象为空"),
    Submit_FAIL(1,"提交失败"),
    Lang_NULL(1,"行数为空，无法查询"),
    PAGE_NULL(1,"页数为空，无法查询"),
    NOT_FOUND(404,"您的页面被偷走了"),
    SERVER_ERROR(500,"服务器出小差了"),
    EXPOR_FAIL(1,"导出失败"),
    ID_ERROR(1,"id有误，无法获取"),
    NOT_PATENT(1,"无匹配数据，查询失败"),
    SELECT_SUCCESS(0,"查询成功");
    ;

    private int status;
    private String message;

    StatusEnum() {
    }

    StatusEnum(int i) {
        this.status = status;
    }

    StatusEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
