package com.sxp.patMag.service.serviceImpl;

import com.sxp.patMag.dao.UploadMapper;
import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.UploadService;
import com.sxp.patMag.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class UploadServiceImpl implements UploadService {

    @Value("${file.accessPath}")
    private String accessPath;

    @Resource
    private UploadMapper uploadMapper;

    @Value("${file.uploadFolder}")
    private String realBasePath;

    @Override
    public GeneralResult insertJbook( HttpServletRequest request) {


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
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return GeneralResult.build(1, "failed");
        } catch (IOException e) {
            e.printStackTrace();
            return GeneralResult.build(1, "failed");
        }

        Jbook jbook = new Jbook();
        jbook.setJbookId(UUID.getUUID());
        jbook.setJbookPatentId(patentId);
        jbook.setJbookUrl(url);
        jbook.setJbookUserId(writePerson);
        jbook.setJbookView("1");

//        uploadMapper.updateJbookStatusByPatentId(patentId);

        int res = uploadMapper.insertJbook(jbook);
        if (res > 0) {
            return GeneralResult.build(0, "success");
        } else {
            return GeneralResult.build(1, "failed");
        }
    }






    /*public String downloadFile(HttpServletRequest request,  HttpServletResponse response) throws UnsupportedEncodingException {

        // 获取指定目录下的第一个文件
        File scFileDir = new File("D://test/");
        File TrxFiles[] = scFileDir.listFiles();
        System.out.println(TrxFiles[0]);
        String fileName = TrxFiles[0].getName(); //下载的文件名

        // 如果文件名不为空，则进行下载
        if (fileName != null) {
            //设置文件路径
            String realPath = "D://test/";
            File file = new File(realPath, fileName);

            // 如果文件名存在，则进行下载
            if (file.exists()) {

                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

                // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("Download the song successfully!");
                } catch (Exception e) {
                    System.out.println("Download the song failed!");
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
*/

}
