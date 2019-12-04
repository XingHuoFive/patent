package com.sxp.patMag.controller;

import com.sxp.patMag.annotation.Monitor;
import com.sxp.patMag.dao.UploadMapper;
import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.LoginService;
import com.sxp.patMag.service.UploadService;
import com.sxp.patMag.util.DownloadUtil;
import com.sxp.patMag.util.GeneralResult;
import com.sxp.patMag.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Author： Jude
 * Date:2019/11/20
 * Time:11:23
 */

@Controller
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;


    @Value("${file.accessPath}")
    private String accessPath;


    @Value("${file.uploadFolder}")
    private String realBasePath;

    /**
     * 实现文件上传
     *
     * */

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult fileUpload( HttpServletRequest request) throws IOException {


        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("fileName");
        // 接收其他表单参数
        String patentId = multipartRequest.getParameter("patentId");
        String writePerson = multipartRequest.getParameter("writePerson");

        if (patentId==null && patentId.length()==0){
            return GeneralResult.build(1, "专利id为空");
        }
        if (writePerson==null && writePerson.length()==0){
            return GeneralResult.build(1, "撰写人为空");
        }
        if (null == file || file.isEmpty()) {
            return GeneralResult.build(1, "文件为空");
        }
        String fileName = file.getOriginalFilename();
//        String projectUrl = request.getSession().getServletContext().getRealPath("/");
//        String path = projectUrl + "/" + fileName;
        Date todayDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(todayDate);
        // 域名访问的相对路径（通过浏览器访问的链接-虚拟路径）
        String saveToPath = accessPath + today + "/";
        // 真实路径，实际储存的路径
        String realPath = realBasePath + today + "/";
        // 储存文件的物理路径，使用本地路径储存
        String filepath = realPath + fileName;
//        String url = DownloadUtil.downloadByUrl(fileName);
        String url = DownloadUtil.downloadByUrl(saveToPath + fileName);
        File dest = new File(filepath);
        //判断文件父目录是否存在
//        if (!dest.getParentFile().exists()) {
//            dest.getParentFile().mkdir();
//        }
        File parent = dest.getParentFile();
        if(!parent.exists()) {
            parent.mkdirs();
        }

        //保存文件
        file.transferTo(dest);

        Jbook jbook = new Jbook();
        jbook.setJbookId(UUID.getUUID());
        jbook.setJbookPatentId(patentId);
        jbook.setJbookUrl(url);
        jbook.setJbookUserId(writePerson);
        jbook.setJbookView("1");


        return uploadService.insertJbook(jbook);

    }
}
