package com.sxp.patMag.service;

import com.sxp.patMag.entity.History;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface HistoryService {

    public GeneralResult insertHistory(History history);

    public GeneralResult selectHistory(String patnetId);

}
