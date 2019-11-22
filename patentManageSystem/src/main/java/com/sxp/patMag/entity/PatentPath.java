package com.sxp.patMag.entity;

import java.util.List;

/**
 * Author： Jude
 * Date:2019/11/22
 * Time:10:56
 */

public class PatentPath {

    /** 专利id **/
    private String patentId;
    /** 案件号 **/
    private String caseNumber;
    /** 申请号 **/
    private String applyNumber;
    /** 申请时间 **/
    private String applyTime;
    /** 撰写人 **/
    private String writePerson;
    /** 申请人/机构 **/

    /**。。。**/
    private String applyPerson;
    /** 发明人 **/
    private String createPerson;
    /** 专利名称 **/
    private String patentName;
    /** 专利进度:1.审核中2.未认领3.未通过4.已通过5.撰写中6.已提交 **/
    private String patentSchedule;
    /** 是否被认领： 认领——未被认领 **/
    private String patentClaim;
    /** 备注 **/
    private String patentRemarks;
    /** 指标 **/
    private List<Indicator> indicatorList;
    /** 路径 **/
    private String path;


    @Override
    public String toString() {
        return "Patent{" +
                "patentId='" + patentId + '\'' +
                ", caseNumber='" + caseNumber + '\'' +
                ", applyNumber='" + applyNumber + '\'' +
                ", applyTime='" + applyTime + '\'' +
                ", writePerson='" + writePerson + '\'' +
                ", applyPerson='" + applyPerson + '\'' +
                ", createPerson='" + createPerson + '\'' +
                ", patentName='" + patentName + '\'' +
                ", patentSchedule='" + patentSchedule + '\'' +
                ", patentClaim='" + patentClaim + '\'' +
                ", patentRemarks='" + patentRemarks + '\'' +
                ", IndicatorList=" + indicatorList +
                '}';
    }


    public String getPatentId() {
        return patentId;
    }

    public void setPatentId(String patentId) {
        this.patentId = patentId;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getApplyNumber() {
        return applyNumber;
    }

    public void setApplyNumber(String applyNumber) {
        this.applyNumber = applyNumber;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getWritePerson() {
        return writePerson;
    }

    public void setWritePerson(String writePerson) {
        this.writePerson = writePerson;
    }

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getPatentName() {
        return patentName;
    }

    public void setPatentName(String patentName) {
        this.patentName = patentName;
    }

    public String getPatentSchedule() {
        return patentSchedule;
    }

    public void setPatentSchedule(String patentSchedule) {
        this.patentSchedule = patentSchedule;
    }

    public String getPatentClaim() {
        return patentClaim;
    }

    public void setPatentClaim(String patentClaim) {
        this.patentClaim = patentClaim;
    }

    public String getPatentRemarks() {
        return patentRemarks;
    }

    public void setPatentRemarks(String patentRemarks) {
        this.patentRemarks = patentRemarks;
    }

    public List<Indicator> getIndicatorList() {
        return indicatorList;
    }

    public void setIndicatorList(List<Indicator> indicatorList) {
        indicatorList = indicatorList;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PatentPath() {
    }

    public PatentPath(String patentId, String caseNumber, String applyNumber, String applyTime, String writePerson, String applyPerson, String createPerson, String patentName, String patentSchedule, String patentClaim, String patentRemarks, List<Indicator> indicatorList) {
        this.patentId = patentId;
        this.caseNumber = caseNumber;
        this.applyNumber = applyNumber;
        this.applyTime = applyTime;
        this.writePerson = writePerson;
        this.applyPerson = applyPerson;
        this.createPerson = createPerson;
        this.patentName = patentName;
        this.patentSchedule = patentSchedule;
        this.patentClaim = patentClaim;
        this.patentRemarks = patentRemarks;
        this.indicatorList = indicatorList;
    }
}
