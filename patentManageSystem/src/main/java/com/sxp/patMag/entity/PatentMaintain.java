package com.sxp.patMag.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.entity
 * @ClassName: PatentMaintain
 * @date 2019/12/2 17:03
 */
public class PatentMaintain {
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
     * 对应字典id
     */
    private int zdId;

    /**
     * 文件id
     */
    private String fileId;

    public PatentMaintain() {
    }

    public PatentMaintain(String patentId, String patentfileURL, int zdId, String fileId) {
        this.patentId = patentId;
        this.patentfileURL = patentfileURL;
        this.zdId = zdId;
        this.fileId = fileId;
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

    public int getZdId() {
        return zdId;
    }

    public void setZdId(int zdId) {
        this.zdId = zdId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "PatentMaintain{" +
                "patentId='" + patentId + '\'' +
                ", patentfileURL='" + patentfileURL + '\'' +
                ", zdId=" + zdId +
                ", fileId='" + fileId + '\'' +
                '}';
    }

}
