package com.sxp.patMag.controller;

import com.sxp.patMag.annotation.Monitor;
import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.service.LoginService;
import com.sxp.patMag.service.UploadService;
import com.sxp.patMag.util.GeneralResult;
import com.sxp.patMag.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

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
    /**
     * 实现文件上传
     * */

    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult fileUpload(@RequestParam("fileName") MultipartFile file, HttpServletRequest request){
           return  uploadService.insertJbook(file,request);
    }
}
