package com.sxp.patMag.service;


import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.util.GeneralResult;

public interface AdminService {

    GeneralResult checkPatent(Patent patent);

    GeneralResult selectAllFilesByPatentId(String patentId);

    GeneralResult readLogFile();
}
