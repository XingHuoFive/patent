package com.sxp.patMag.util;

import com.sxp.patMag.entity.IndicatorExport;
import com.sxp.patMag.entity.Patent;

/**
 * Author： 硕
 * Date:2019/11/25
 * Time:10:13
 */

public class CheckOut {


    public static GeneralResult checkOutLength(Patent patent){
        if (patent.getApplyTime().length() > 50){
            return GeneralResult.build(1,"申请时间长度过长",null);
        }
        if(patent.getApplyNumber().length() > 50){
            return GeneralResult.build(1,"申请号长度过长",null);
        }
        if(patent.getPatentName().length() > 100){
            return GeneralResult.build(1,"专利名字长度过长",null);
        }
        if(patent.getApplyPerson().length() > 50){
            return GeneralResult.build(1,"申请人长度过长",null);
        }
        if(patent.getCreatePerson().length() > 50){
            return GeneralResult.build(1,"发明人长度过长",null);
        }
        if(patent.getCaseNumber().length() > 50){
            return GeneralResult.build(1,"案例号长度过长",null);
        }else{
            return null;
        }

    }


    public static GeneralResult checkOutNull(Patent patent){
        if (patent.getApplyTime() == null){
            return GeneralResult.build(1,"申请时间为空",null);
        }
        if(patent.getApplyNumber() == null){
            return GeneralResult.build(1,"申请号为空",null);
        }
        if(patent.getPatentName() == null){
            return GeneralResult.build(1,"专利名字为空",null);
        }
        if(patent.getApplyPerson() == null){
            return GeneralResult.build(1,"申请人为空",null);
        }
        if(patent.getCreatePerson() == null){
            return GeneralResult.build(1,"发明人为空",null);
        }
        if(patent.getCaseNumber() == null){
            return GeneralResult.build(1,"案例号为空",null);
        }else{
            return null;
        }

    }

    public static boolean checkOutUpdate(Patent patent) {
        boolean flag = false;
        if (null != patent.getApplyNumber()) {
            if (patent.getApplyNumber().length() > 50) {
                return flag;
            }
        }
        if (null != patent.getCaseNumber()) {
            if (patent.getCaseNumber().length() > 16) {
                return flag;
            }
        }
        if (null != patent.getApplyTime()) {
            if (patent.getApplyTime().length() > 30) {
                return flag;
            }
        }
        if (null != patent.getWritePerson()) {
            if (patent.getWritePerson().length() > 50) {
                return flag;
            }
        }
        if (null != patent.getApplyPerson()) {
            if (patent.getApplyPerson().length() > 50) {
                return flag;
            }
        }
        if (null != patent.getCreatePerson()) {
            if (patent.getCreatePerson().length() > 30) {
                return flag;
            }
        }
        if (null != patent.getPatentName()) {
            if (patent.getPatentName().length() > 150) {
                return flag;
            }
        }
        if (null != patent.getPatentRemarks()) {
            if (patent.getPatentRemarks().length() > 500) {
                return flag;
            }
        }
        flag = true;
        return flag;
    }

    public static boolean checkOutIndicatorSelect(IndicatorExport indicatorExport) {
        if (null != indicatorExport.getWritePerson()) {
            if (indicatorExport.getWritePerson().length() > 50) {
                return false;
            }
        }
        if (null != indicatorExport.getPatentSchedule()) {
            if (indicatorExport.getPatentSchedule().length() > 10) {
                return false;
            }
        }
        if (null != indicatorExport.getIndicatorName()) {
            if (indicatorExport.getIndicatorName().length() > 100) {
                return false;
            }
        }
        if (null != indicatorExport.getCaseNumber()) {
            if (indicatorExport.getCaseNumber().length() > 16) {
                return false;
            }
        }
        if (null != indicatorExport.getApplyNumber()) {
            if (indicatorExport.getApplyNumber().length() > 16) {
                return false;
            }
        }
        if (null != indicatorExport.getApplyTime()) {
            if (indicatorExport.getApplyTime().length() > 30) {
                return false;
            }
        }
        if (null != indicatorExport.getCreatePerson()) {
            if (indicatorExport.getCreatePerson().length() > 30) {
                return false;
            }
        }
        return true;
    }

}
