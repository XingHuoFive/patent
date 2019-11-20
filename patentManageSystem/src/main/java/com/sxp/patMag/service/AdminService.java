package com.sxp.patMag.service;

import java.util.List;

public interface AdminService {

    boolean checkPatent(String patentId, String updateField);

    List<String> selectAllFilesByPatentId(String patentId);
}
