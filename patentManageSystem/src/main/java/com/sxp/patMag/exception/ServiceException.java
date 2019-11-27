package com.sxp.patMag.exception;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.exception
 * @ClassName: ServiceException
 * @date 2019/11/26 20:38
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 自定义异常模板
     */
    private ExceptionEnum exceptionEnum;

    /**
     * 自定义异常信息
     */
    private String errorDetail;

    /**
     * 带自定义异常信息的构造方法
     * @param exceptionEnum
     * @param errorDetail
     */
    public ServiceException(ExceptionEnum exceptionEnum,String errorDetail){
        this.exceptionEnum = exceptionEnum;
        this.errorDetail = errorDetail;
    }

    /**
     * 模版异常的构造方法
     * @param exceptionEnum
     */
    public ServiceException(ExceptionEnum exceptionEnum){
        this.exceptionEnum = exceptionEnum;
    }

    public ExceptionEnum getExceptionEnum(){
        return exceptionEnum;
    }

    public void setExceptionEnum(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
