package com.sxp.patMag.service;

import com.sxp.patMag.entity.JBook;

import java.util.List;

public interface AdminService {

    boolean checkPatent(String patentId, String updateField);

    List<JBook> selectAllFilesByPatentId(String patentId);
}
