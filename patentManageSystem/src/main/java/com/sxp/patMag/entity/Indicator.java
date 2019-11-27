package com.sxp.patMag.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.entity
 * @ClassName: Indicator
 * @date 2019/11/19 18:43
 */
public class Indicator {

    /**
     * 指标id
     */
    @Size(max = 32, min = 32, message = "指标id长度有误")
    private String indicatorId;


    /**
     * 指标名
     */
    @Max(value = 100, message = "指标名过长")
    private String indicatorName;

    /**
     * 指标对应专利id
     */
    @Size(max = 32, min = 32, message = "专利id长度有误")
    private String patentId;

    public Indicator(String indicatorId, String indicatorName, String patentId) {
        this.indicatorId = indicatorId;
        this.indicatorName = indicatorName;
        this.patentId = patentId;
    }

    public Indicator() {
    }

    public String getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(String indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getPatentId() {
        return patentId;
    }

    public void setPatentId(String patentId) {
        this.patentId = patentId;
    }

    @Override
    public String toString() {
        return "Indicator{" +
                "indicatorId='" + indicatorId + '\'' +
                ", indicatorName='" + indicatorName + '\'' +
                ", patentId='" + patentId + '\'' +
                '}';
    }
}
