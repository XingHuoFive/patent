package com.sxp.patMag.entity;

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
    private String indicatorId;

    /**
     * 指标名
     */
    private String indicatorName;

    /**
     * 指标对应专利id
     */
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
