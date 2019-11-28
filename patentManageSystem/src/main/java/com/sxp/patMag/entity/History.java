package com.sxp.patMag.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author： Jude
 * @date:2019/11/20
 * @time:19:39
 */

public class History {

    /**
     * 历史id
     **/
    @Size(max = 32, min = 16,message="id超过范围")
    private String htId;
    /**
     * 创建时间
     **/
    @Size(max = 30,min = 0,message="日期超过范围")
    private String htDate;
    /**
     * 操作用户
     **/
    @Size(max = 50,min = 0,message="用户id超过范围")
    @NotNull(message = "操作用户不能为空")
    private String htUserId;
    /**
     * 进程
     **/
    @Size(max = 20,min = 0,message="进程超过范围")
    private String htProcess;
    /**
     * 修改旧
     **/
    @Size(max = 255,min = 0,message="旧修改项超过范围")
    private String htOldItem;
    /**
     * 修改新
     **/
    @Size(max = 255,min = 0,message="新修改项超过范围")
    @NotNull(message = "新修改项不能为空")
    private String htNewItem;
    /**
     * 专利ID
     **/
    @Size(max = 32,min = 16,message="专利号超过范围")
    private String htPatentId;
    /**
     * 操作
     **/
    @Size(max = 20,min = 0,message="操作超过范围")
    private String htOperation;


    private Patent patentPojo;

    public Patent getPatentPojo() {
        return patentPojo;
    }

    public void setPatentPojo(Patent patentPojo) {
        this.patentPojo = patentPojo;
    }

    private User userPojo;

    public User getUserPojo() {
        return userPojo;
    }

    public void setUserPojo(User userPojo) {
        this.userPojo = userPojo;
    }

    @Override
    public String toString() {
        return "History{" +
                "htId='" + htId + '\'' +
                ", htDate='" + htDate + '\'' +
                ", htUserId='" + htUserId + '\'' +
                ", htProcess='" + htProcess + '\'' +
                ", htOldItem='" + htOldItem + '\'' +
                ", htNewItem='" + htNewItem + '\'' +
                ", htPatentId='" + htPatentId + '\'' +
                ", htOperation='" + htOperation + '\'' +
                ", userPojo=" + userPojo +
                '}';
    }

    public String getHtOperation() {
        return htOperation;
    }

    public void setHtOperation(String htOperation) {
        this.htOperation = htOperation;
    }

    public String getHtId() {
        return htId;
    }

    public void setHtId(String htId) {
        this.htId = htId;
    }

    public String getHtDate() {
        return htDate;
    }

    public void setHtDate(String htDate) {
        this.htDate = htDate;
    }

    public String getHtUserId() {
        return htUserId;
    }

    public void setHtUserId(String htUserId) {
        this.htUserId = htUserId;
    }

    public String getHtProcess() {
        return htProcess;
    }

    public void setHtProcess(String htProcess) {
        this.htProcess = htProcess;
    }

    public String getHtOldItem() {
        return htOldItem;
    }

    public void setHtOldItem(String htOldItem) {
        this.htOldItem = htOldItem;
    }

    public String getHtNewItem() {
        return htNewItem;
    }

    public void setHtNewItem(String htNewItem) {
        this.htNewItem = htNewItem;
    }

    public String getHtPatentId() {
        return htPatentId;
    }

    public void setHtPatentId(String htPatentId) {
        this.htPatentId = htPatentId;
    }
}
