package com.sxp.patMag.entity;

/**
 * Author： Jude
 * Date:2019/11/20
 * Time:11:43
 */

public class Jbook {


    private String jbookId;

    private String jbookPatentId;

    private String jbookUrl;

    private String jbookUserId;
    private String jbookView;


    @Override
    public String toString() {
        return "Jbook{" +
                "jbookId='" + jbookId + '\'' +
                ", jbookPatentId='" + jbookPatentId + '\'' +
                ", jbookUrl='" + jbookUrl + '\'' +
                ", jbookUserId='" + jbookUserId + '\'' +
                ", jbookView='" + jbookView + '\'' +
                '}';
    }

    public String getJbookId() {
        return jbookId;
    }

    public void setJbookId(String jbookId) {
        this.jbookId = jbookId;
    }

    public String getJbookPatentId() {
        return jbookPatentId;
    }

    public void setJbookPatentId(String jbookPatentId) {
        this.jbookPatentId = jbookPatentId;
    }

    public String getJbookUrl() {
        return jbookUrl;
    }

    public void setJbookUrl(String jbookUrl) {
        this.jbookUrl = jbookUrl;
    }

    public String getJbookUserId() {
        return jbookUserId;
    }

    public void setJbookUserId(String jbookUserId) {
        this.jbookUserId = jbookUserId;
    }

    public String getJbookView() {
        return jbookView;
    }

    public void setJbookView(String jbookView) {
        this.jbookView = jbookView;
    }
}
