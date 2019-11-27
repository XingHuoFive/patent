package com.sxp.patMag.exception;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.exception
 * @ClassName: PatentException
 * @date 2019/11/26 20:34
 */
public enum PatentException implements ExceptionEnum {

    NULL_PRING(10001, "空指针异常"),
    EXPORT_ERROR(10002, "导出失败"),
    USERNAME_OR_PASSWORD_ERR(10003, "用户名或密码错误"),
    EMPTY_PARAME(10004, "参数为空"),
    ERROR_PARAME(10005, "参数错误"),
    HTTP_POST_ERR(10006, "http post 请求异常"),
    HTTP_GET_ERR(10007, "http get 请求异常"),
    GENERAL_ERR(10008, "一般异常");


    public int code;
    public String message;

    PatentException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMessage() {
        return message;
    }}
