package com.sxp.patMag.service;

import com.sxp.patMag.entity.Patent;

import java.util.List;

/**
 * @author Mrs.Wang
 * @create 2019-11-20 14:54
 */
public interface PatentSelectOrderByTimeService {
    List<Patent> selectPatentByPatentOrderbyTime(Patent patent);
    List<Patent> selectPatentToAdmin();
}
