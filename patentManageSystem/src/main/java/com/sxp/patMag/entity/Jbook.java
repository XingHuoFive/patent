package com.sxp.patMag.entity;

/**
 * Author： Jude
 * Date:2019/11/20
 * Time:11:44
 */

public class JBook {
    private String jBookId;
    private String jBookPatentId;
    private String jBookUrl;
    private String jBookUserId;
    private String jBookView;

    public JBook() {
    }

    public JBook(String jBookId, String jBookPatentId, String jBookUrl, String jBookUserId, String jBookView) {
        this.jBookId = jBookId;
        this.jBookPatentId = jBookPatentId;
        this.jBookUrl = jBookUrl;
        this.jBookUserId = jBookUserId;
        this.jBookView = jBookView;
    }

    public String getjBookId() {
        return jBookId;
    }

    public void setjBookId(String jBookId) {
        this.jBookId = jBookId;
    }

    public String getjBookPatentId() {
        return jBookPatentId;
    }

    public void setjBookPatentId(String jBookPatentId) {
        this.jBookPatentId = jBookPatentId;
    }

    public String getjBookUrl() {
        return jBookUrl;
    }

    public void setjBookUrl(String jBookUrl) {
        this.jBookUrl = jBookUrl;
    }

    public String getjBookUserId() {
        return jBookUserId;
    }

    public void setjBookUserId(String jBookUserId) {
        this.jBookUserId = jBookUserId;
    }

    public String getjBookView() {
        return jBookView;
    }

    public void setjBookView(String jBookView) {
        this.jBookView = jBookView;
    }

    @Override
    public String toString() {
        return "JBook{" +
                "jBookId='" + jBookId + '\'' +
                ", jBookPatentId='" + jBookPatentId + '\'' +
                ", jBookUrl='" + jBookUrl + '\'' +
                ", jBookUserId='" + jBookUserId + '\'' +
                ", jBookView='" + jBookView + '\'' +
                '}';
    }
}
