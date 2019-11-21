package com.sxp.patMag.entity;

/**
 * Author： Jude
 * Date:2019/11/20
 * Time:19:39
 */

public class History {

    private String htId;
    private String htDate;
    private String htUserId;
    private String htProcess;
    private String htOldItem;
    private String htNewItem;
    private String htPatentId;
    private String htOperation;
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
