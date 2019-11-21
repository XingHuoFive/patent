package com.sxp.patMag.service;

import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.util.GeneralResult;

import java.util.List;

public interface AdminService {

    GeneralResult checkPatent(String patentId, String updateField);

    GeneralResult selectAllFilesByPatentId(String patentId);

    GeneralResult readLogFile();
}
