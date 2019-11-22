package com.sxp.patMag.entity;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.entity
 * @ClassName: IndicatorExport
 * @date 2019/11/20 20:07
 */
public class IndicatorExport {


    /**
     * 指标id
     */
    private String indicatorId;

    /**
     * 编号
     */
    private String number;

    /**
     * 指标名
     */
    private String indicatorName;

    /**
     * 所属专利
     */
    private String caseNumber;

    /**
     * 专利进度:1.审核中2.未认领3.未通过4.已通过5.撰写中6.已提交
     **/
    private String patentSchedule;

    /**
     * 申请日
     */
    private String applyTime;

    /**
     * 发明人
     **/
    private String createPerson;

    /**
     * 撰写人
     */
    private String writePerson;

    /**
     * 申请号
     **/
    private String applyNumber;
    public IndicatorExport() {
    }

    public IndicatorExport(String indicatorId, String number, String indicatorName, String caseNumber, String patentSchedule, String applyTime, String createPerson, String writePerson, String applyNumber) {
        this.indicatorId = indicatorId;
        this.number = number;
        this.indicatorName = indicatorName;
        this.caseNumber = caseNumber;
        this.patentSchedule = patentSchedule;
        this.applyTime = applyTime;
        this.createPerson = createPerson;
        this.writePerson = writePerson;
        this.applyNumber = applyNumber;
    }

    public String getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(String indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getPatentSchedule() {
        return patentSchedule;
    }

    public void setPatentSchedule(String patentSchedule) {
        this.patentSchedule = patentSchedule;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getWritePerson() {
        return writePerson;
    }

    public void setWritePerson(String writePerson) {
        this.writePerson = writePerson;
    }

    public String getApplyNumber() {
        return applyNumber;
    }

    public void setApplyNumber(String applyNumber) {
        this.applyNumber = applyNumber;
    }

    @Override
    public String toString() {
        return "IndicatorExport{" +
                "indicatorId='" + indicatorId + '\'' +
                ", number='" + number + '\'' +
                ", indicatorName='" + indicatorName + '\'' +
                ", caseNumber='" + caseNumber + '\'' +
                ", patentSchedule='" + patentSchedule + '\'' +
                ", applyTime='" + applyTime + '\'' +
                ", createPerson='" + createPerson + '\'' +
                ", writePerson='" + writePerson + '\'' +
                ", applyNumber='" + applyNumber + '\'' +
                '}';
    }
}
