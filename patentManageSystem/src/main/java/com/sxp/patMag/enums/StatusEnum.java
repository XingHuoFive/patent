package com.sxp.patMag.enums;


/**
 * @author 硕
 * @date
 */
public enum StatusEnum {


    Success(0,"成功"),
    Fail(1,"无匹配专利"),
    Objeck_NULL(1,"对象为空"),
    Submit_FAIL(1,"提交失败");


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
