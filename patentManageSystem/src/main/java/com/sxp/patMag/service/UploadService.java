package com.sxp.patMag.service;

import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface UploadService {

    public GeneralResult insertJbook(MultipartFile file, Patent patent, HttpServletRequest request );

}
