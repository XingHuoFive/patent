package com.sxp.patMag.service.serviceImpl;


import com.sxp.patMag.dao.HistoryMapper;
import com.sxp.patMag.dao.UploadMapper;
import com.sxp.patMag.entity.History;
import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.service.HistoryService;
import com.sxp.patMag.service.UploadService;
import com.sxp.patMag.util.GeneralResult;
import com.sxp.patMag.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Resource
    private HistoryMapper historyMapper;

    /**
     * @param history
     * @return 插入流程历史
     */
    public GeneralResult insertHistory(History history) {
        int a = historyMapper.insertHistory(history);
        if (a > 0) {
            return GeneralResult.build(0, "success");
        }
        return GeneralResult.build(1, "failed");
    }

    /**
     * @param patnetId
     * @return 根据XXX查询历史记录
     */

    public GeneralResult selectHistory(String patnetId) {
        List<History> historyList = historyMapper.selectHistory(patnetId);
        if (historyList == null || historyList.size() == 0) {
            return GeneralResult.build(1, "failed");
        }
        return GeneralResult.build(0, "success", historyList);
    }

}
