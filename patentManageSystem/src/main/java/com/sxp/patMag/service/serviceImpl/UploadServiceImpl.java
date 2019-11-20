package com.sxp.patMag.service.serviceImpl;


import com.sxp.patMag.dao.LoginMapper;
import com.sxp.patMag.dao.UploadMapper;
import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.service.LoginService;
import com.sxp.patMag.service.UploadService;
import com.sxp.patMag.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class UploadServiceImpl implements UploadService {

    @Resource
    private UploadMapper uploadMapper;
  @Value("${visualPath}")
  private  String visualPath;

    public GeneralResult insertJbook(MultipartFile file, HttpServletRequest request) {

        if (file.isEmpty()) {
            return GeneralResult.build(1, "failed");
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();

        String projectUrl =  request.getSession().getServletContext().getRealPath("/");
        String path = projectUrl + "/" + fileName;
        String url = visualPath + fileName;
        File dest = new File(path);
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件

        } catch (IllegalStateException e) {
            e.printStackTrace();
            return GeneralResult.build(1, "failed");
        } catch (IOException e) {
            e.printStackTrace();
            return GeneralResult.build(1, "failed");
        }

        Jbook jbook = new Jbook();
        jbook.setJbookId(UUID.getUUID());
        jbook.setJbookPatentId("12");
        jbook.setJbookUrl(url);
        jbook.setJbookUserId("1");
        jbook.setJbookView("true");

        int res = uploadMapper.insertJbook(jbook);
        if (res > 0) {
            return GeneralResult.build(0, "success");
        } else {
            return GeneralResult.build(1, "failed");
        }
    }
}
