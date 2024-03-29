package com.sxp.patMag.service.serviceImpl;


import com.sxp.patMag.annotation.Monitor;
import com.sxp.patMag.dao.PatentMapper;
import com.sxp.patMag.entity.IndicatorExport;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.PatentFileMaintain;
import com.sxp.patMag.entity.PatentMaintain;
import com.sxp.patMag.service.PatentService;
import com.sxp.patMag.util.DownloadUtil;
import com.sxp.patMag.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.serviceImpl
 * @ClassName: PatentServiceImpl
 * @date 2019/11/26 17:08
 */
@Service
public class PatentServiceImpl implements PatentService {

    @Resource
    private PatentMapper tbPatentMapper;

    @Value("${file.accessPath}")
    private String accessPath;

    @Value("${file.uploadFolder}")
    private String realBasePath;

    private static final Map<String, String> fileTypeMap = new HashMap<>();

    static {
        fileTypeMap.put("申请文件", "3");
        fileTypeMap.put("受理通知书", "4");
        fileTypeMap.put("初审合格通知书", "5");
        fileTypeMap.put("公布及进入实审通知书", "6");
        fileTypeMap.put("答复文件", "7");
        fileTypeMap.put("授权办登通知书", "8");
        fileTypeMap.put("驳回通知书", "9");
        fileTypeMap.put("证书", "10");
    }

    @Override
    public Patent selectById(String patentId) {
        return tbPatentMapper.getById(patentId);
    }

    @Override
    public List<IndicatorExport> list() {
        return tbPatentMapper.getPatentList();
    }

    @Override
    public List<IndicatorExport> listByPatent(IndicatorExport indicatorExport) {
        return tbPatentMapper.getPatentListByVO(indicatorExport);
    }

    @Override
    public IndicatorExport getById(String indicatorId) {
        return tbPatentMapper.getIndicatorById(indicatorId);
    }

    @Override
    @Monitor("修改专利")
    public int updatePatent(Patent patent) {
        return tbPatentMapper.updatePatent(patent);
    }

    @Override
    public List<String> JbookUrlList(String patentId) {
        return tbPatentMapper.getJbookUrlList(patentId);
    }

    @Override
    @Monitor("提交")
    public int submitPatent(Patent patent) {
        return tbPatentMapper.submitPatent(patent);
    }

    @Override
    public int noSubmitPatent(Patent patent) {
        return tbPatentMapper.noSubmitPatent(patent);
    }

    @Override
    public List<Map<String, String>> getMaintainList() {
        List<String> maintainList = tbPatentMapper.getMaintainList();
        if (null == maintainList || maintainList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Map<String, String>> res = new ArrayList<>(maintainList.size());
        for (int i = 0; i < maintainList.size(); i++) {
            Map<String, String> maintainMap = new HashMap<>(2);
            maintainMap.put("label", maintainList.get(i));
            maintainMap.put("value", maintainList.get(i));
            res.add(maintainMap);
        }
        return res;
    }

    @Override
    public List<PatentFileMaintain> getFileUrlByPatentId(PatentFileMaintain patentFileMaintain) {
        return tbPatentMapper.getFileURLByPatentId(patentFileMaintain);
    }

    @Override
    @Monitor("上传文件")
    public int uploadFile(HttpServletRequest request, String fileName) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("fileName");
        String patentId = multipartRequest.getParameter("patentId");
        String fileType = multipartRequest.getParameter("fileType");
        if (null == file || file.isEmpty()) {
            return 0;
        }
        if (null == patentId || "".equals(patentId)) {
            return 0;
        }
        if (null == fileType || "".equals(fileType) || null == fileTypeMap.get(fileType)) {
            return 0;
        }
        Date todayDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(todayDate);
        String saveToPath = accessPath + today + "/";
        String realPath = realBasePath + today + "/";
        String filepath = realPath + fileName;
        String url = DownloadUtil.downloadByUrl(saveToPath + fileName);
        File dest = new File(filepath);
        File parent = dest.getParentFile();
        if(!parent.exists()) {
            parent.mkdirs();
        }
        file.transferTo(dest);
        String zdId = fileTypeMap.get(fileType);
        int i = tbPatentMapper.updateView(patentId,zdId);
        PatentMaintain patentMaintain = new PatentMaintain();
        if (i != 1) {
            patentMaintain.setFileView("1");
        }
        patentMaintain.setFileId(UUID.getUUID());
        patentMaintain.setPatentfileURL(url);
        patentMaintain.setPatentId(patentId);
        patentMaintain.setZdId(zdId);
        return tbPatentMapper.uploadFile(patentMaintain);
    }

    @Override
    public String getStatus(Patent patent) {
        return tbPatentMapper.getStatus(patent);
    }


}
