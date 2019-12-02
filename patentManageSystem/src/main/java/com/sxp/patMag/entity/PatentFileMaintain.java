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
}
