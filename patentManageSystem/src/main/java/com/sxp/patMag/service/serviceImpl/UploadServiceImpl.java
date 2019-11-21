package com.sxp.patMag.service.serviceImpl;


import com.sxp.patMag.dao.LoginMapper;
import com.sxp.patMag.dao.UploadMapper;
import com.sxp.patMag.entity.Jbook;
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
    private String visualPath;

    public GeneralResult insertJbook(MultipartFile file, HttpServletRequest request) {

        if (file.isEmpty()) {
            return GeneralResult.build(1, "failed");
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();

        String projectUrl = request.getSession().getServletContext().getRealPath("/");
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
