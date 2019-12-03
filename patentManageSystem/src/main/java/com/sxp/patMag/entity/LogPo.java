package com.sxp.patMag.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author： Jude
 * @date:2019/11/20
 * @time:19:39
 */

public class LogPo {

    /**
     * 修改人
     **/
    @Size(max = 32, min = 0,message="修改人")
    private String userName;
    /**
     * 生成时间
     **/
    @Size(max = 30,min = 0,message="生成时间")
    private String createTime;

    /**
     * 操作
     **/
    @Size(max = 20,min = 0,message="操作超过范围")
    private String operation;


    /**
     * 修改
     **/
    @Size(max = 255,min = 0,message="修改项超过范围")
    private String item;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "LogPo{" +
                "userName='" + userName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", operation='" + operation + '\'' +
                ", item='" + item + '\'' +
                '}';
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
