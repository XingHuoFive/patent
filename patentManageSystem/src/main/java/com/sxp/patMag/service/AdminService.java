package com.sxp.patMag.service;

import com.sxp.patMag.entity.Jbook;

import java.util.List;

public interface AdminService {

    boolean checkPatent(String patentId, String updateField);

    List<Jbook> selectAllFilesByPatentId(String patentId);

    List<String> readLogFile();
}
