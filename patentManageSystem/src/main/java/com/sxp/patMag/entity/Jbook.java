package com.sxp.patMag.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Author： Jude
 * Date:2019/11/20
 * Time:11:44
 */

public class Jbook {
    /**
     * id主键
     */
    @Size(max = 32,min = 16,message="id超过范围")
    private String jbookId;
    /**
     * 专利外键
     */
    @Size(max = 32,min = 16,message="专利id超过范围")
    private String jbookPatentId;
    /**
     * URL
     */
    @Size(max = 255,min = 0,message="链接地址")
    @NotNull(message = "交底书链接为空")
    private String jbookUrl;
    /**
     * 用户id
     */
    @Size(max = 32,min = 16,message="用户id超过范围")
    @NotNull(message = "用户名不能为空")
    private String jbookUserId;
    /**
     * 是否是最新
     */
    @Size(max = 10,min = 0,message="判断值view超过范围")
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
