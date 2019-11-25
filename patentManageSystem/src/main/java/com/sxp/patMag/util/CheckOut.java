package com.sxp.patMag.util;

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
            return GeneralResult.build(1,"申请时间长度过长",null);
        }
        if(patent.getApplyNumber() == null){
            return GeneralResult.build(1,"申请号长度过长",null);
        }
        if(patent.getPatentName() == null){
            return GeneralResult.build(1,"专利名字长度过长",null);
        }
        if(patent.getApplyPerson() == null){
            return GeneralResult.build(1,"申请人长度过长",null);
        }
        if(patent.getCreatePerson() == null){
            return GeneralResult.build(1,"发明人长度过长",null);
        }
        if(patent.getCaseNumber() == null){
            return GeneralResult.build(1,"案例号长度过长",null);
        }else{
            return null;
        }

    }
}
