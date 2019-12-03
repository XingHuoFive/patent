package com.sxp.patMag.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.entity
 * @ClassName: PatentFileMaintain
 * @date 2019/12/2 18:01
 */
public class PatentFileMaintain {
    /**
     * 专利id
     **/
    @Size(max = 32, min = 16, message = "专利id长度过长")
    @NotNull
    private String patentId;

    /**
     * 专利文件下载链接
     */
    private String patentfileURL;

    /**
     * 专利文件类型
     */
    private String zdFile;

    public PatentFileMaintain() {
    }

    public PatentFileMaintain(String patentId, String patentfileURL, String zdFile) {
        this.patentId = patentId;
        this.patentfileURL = patentfileURL;
        this.zdFile = zdFile;
    }

    public String getPatentId() {
        return patentId;
    }

    public void setPatentId(String patentId) {
        this.patentId = patentId;
    }

    public String getPatentfileURL() {
        return patentfileURL;
    }

    public void setPatentfileURL(String patentfileURL) {
        this.patentfileURL = patentfileURL;
    }

    public String getZdFile() {
        return zdFile;
    }

    public void setZdFile(String zdFile) {
        this.zdFile = zdFile;
    }

    @Override
    public String toString() {
        return "PatentFileMaintain{" +
                "patentId='" + patentId + '\'' +
                ", patentfileURL='" + patentfileURL + '\'' +
                ", zdFile='" + zdFile + '\'' +
                '}';
    }
    
}
