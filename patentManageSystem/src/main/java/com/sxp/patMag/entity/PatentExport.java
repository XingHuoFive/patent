package com.sxp.patMag.entity;

/**
 * Author：Shuo
 * Date:2019/11/21
 * Time:14:07
 */

public class PatentExport {

    /** 编号 **/
    private String  number;
    /** 专利名称 **/
    private String patentName;
    /** 案件号 **/
    private String caseNumber;
    /** 申请号 **/
    private String applyNumber;
    /** 专利进度:1.审核中2.未认领3.未通过4.已通过5.撰写中6.已提交 **/
    private String patentSchedule;
    /** 申请时间 **/
    private String applyTime;
    /** 发明人 **/
    private String createPerson;
    /** 撰写人 **/
    private String writePerson;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPatentName() {
        return patentName;
    }

    public void setPatentName(String patentName) {
        this.patentName = patentName;
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

    @Override
    public String toString() {
        return "PatentExport{" +
                "patentName='" + patentName + '\'' +
                ", caseNumber='" + caseNumber + '\'' +
                ", applyNumber='" + applyNumber + '\'' +
                ", patentSchedule='" + patentSchedule + '\'' +
                ", applyTime='" + applyTime + '\'' +
                ", createPerson='" + createPerson + '\'' +
                ", writePerson='" + writePerson + '\'' +
                '}';
    }

    public PatentExport() {
    }
}
