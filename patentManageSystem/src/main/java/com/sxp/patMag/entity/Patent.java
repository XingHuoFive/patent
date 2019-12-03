
package com.sxp.patMag.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author： Jude
 * @Date:2019/11/19
 * @Time:18:18
 */
public class Patent {
    /**
     * 专利id
     **/
    @Size(max = 32, min = 16,message = "专利id长度过长")
    private String patentId;
    /**
     * 案件号
     **/
    @Size(max = 16,min = 0,message = "案件号长度过长")
    private String caseNumber;
    /**
     * 申请号
     **/
    @Size(max = 16,min = 0,message = "申请号长度过长")
    private String applyNumber;
    /**
     * 申请时间
     **/

    @Size(max = 30,min = 0,message = "申请时间长度过长")
    private String applyTime;
    /**
     * 撰写人
     **/
    @Size(max = 50,min = 0,message = "撰写人长度过长")
    private String writePerson;
    /** 申请人/机构 **/

    /**
     *
     **/
    @Size(max = 50,min = 0,message = "申请人长度过长")
    private String applyPerson;
    /**
     * 发明人
     **/
    @Size(max = 30,min = 0,message = "发明人长度过长")
    private String createPerson;
    /**
     * 专利名称
     **/
    @Size(max = 150,min = 0,message = "专利名称长度过长")
    private String patentName;
    /**
     * 专利进度:1.审核中2.待认领3.未通过4.编写中5.已提交
     **/
    @Size(max = 10,min = 0,message = "专利进度长度过长")
    private String patentSchedule;
    /**
     * 是否被认领： 认领——未被认领
     **/
    @Size(max = 10,min = 0)
    private String patentClaim;
    /**
     * 备注
     **/
    @Size(max = 255,min = 0,message = "备注长度过长")
    private String patentRemarks;
    /**
     * 指标
     **/
    private List<Indicator> indicatorList = new ArrayList<>();
    /**
     * 是否通过
     */
    private String spare;
    /**
     * 专利交底书
     */
    private List<Jbook> jbookList;

    @Override
    public String toString() {
    return " {" +
            " \"patentId\" : \"" + patentId + '\"' +
            ", \"caseNumber\" : \"" + caseNumber + '\"' +
            ", \"applyNumber\" : \"" + applyNumber + '\"' +
            ", \"applyTime\" : \"" + applyTime + '\"' +
            ", \"writePerson\" : \"" + writePerson + '\"' +
            ", \"applyPerson\" : \"" + applyPerson + '\"' +
            ", \"createPerson\" : \"" + createPerson + '"' +
            ", \"patentName\" : \"" + patentName + '\"' +
            ", \"patentSchedule\" : \"" + patentSchedule + '\"' +
            ", \"patentClaim\" : \"" + patentClaim + '\"' +
            ", \"patentRemarks\" : \"" + patentRemarks + '\"' +
            ", \"spare\" : \"" + spare + '\"' +
            '}';
}

    public String getPatentId() {
        return patentId;
    }

    public List<Indicator> getIndicatorList() {
        return indicatorList;
    }

    public void setIndicatorList(List<Indicator> indicatorList) {
        this.indicatorList = indicatorList;
    }

    public void setPatentId(String patentId) {
        this.patentId = patentId;
    }

    public String getSpare() {
        return spare;
    }

    public void setSpare(String spare) {
        this.spare = spare;
    }

    public List<Jbook> getJbookList() {
        return jbookList;
    }

    public void setJbookList(List<Jbook> jbookList) {
        this.jbookList = jbookList;
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

    public Patent() {

    }

    public Patent(String patentId, String caseNumber, String applyNumber, String applyTime, String writePerson, String applyPerson, String createPerson, String patentName, String patentSchedule, String patentClaim, String patentRemarks, List<Indicator> indicatorList, String spare) {
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
        this.spare = spare;
    }

    public Patent(String patentId, String caseNumber, String applyNumber, String applyTime, String writePerson, String applyPerson, String createPerson, String patentName, String patentSchedule, String patentClaim, String patentRemarks, List<Indicator> indicatorList) {
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
