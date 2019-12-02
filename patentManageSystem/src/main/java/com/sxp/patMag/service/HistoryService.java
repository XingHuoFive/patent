package com.sxp.patMag.service;

import com.sxp.patMag.entity.History;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
/**
 * @author： Jude
 * @date:2019/11/26
 * @time:11:21
 */

public interface HistoryService {

     GeneralResult insertHistory(History history);

     GeneralResult selectHistory(Patent patent);

     GeneralResult selectHistoryByPatentId( String patentId);
}
