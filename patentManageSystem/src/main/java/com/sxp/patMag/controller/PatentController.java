package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.PatentFileMaintain;
import com.sxp.patMag.enums.StatusEnum;
import com.sxp.patMag.exception.PatentException;
import com.sxp.patMag.exception.ServiceException;
import com.sxp.patMag.service.PatentService;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.controller
 * @ClassName: PatentController
 * @date 2019/11/26 17:08
 */
@RestController
@RequestMapping("/patent")
public class PatentController {

    @Autowired
    private PatentService tbPatentService;

    @PostMapping("/selectPatentById/{patentId}")
    @ResponseBody
    public GeneralResult selectTbPatentById(@PathVariable("patentId") String patentId) {
        GeneralResult generalResult = new GeneralResult();
        try {
            if (null == patentId || "".equals(patentId)) {
                generalResult.setStatus(1);
                generalResult.setMsg("id为空，无法查询");
                return generalResult;
            }
            Patent patent = tbPatentService.selectById(patentId);
            if (null == patent) {
                generalResult.setStatus(1);
                generalResult.setMsg("未找到对应专利");
                return generalResult;
            }
            generalResult.setStatus(0);
            generalResult.setMsg("查询成功");
            generalResult.setData(patent);
        } catch (ServiceException e) {
            generalResult.setStatus(1);
            generalResult.setMsg(e.getMessage());
            return generalResult;
        }
        return generalResult;
    }

    @PostMapping("/update")
    public GeneralResult update(@RequestBody Patent patent, BindingResult bindingResult) {
        GeneralResult generalResult = new GeneralResult();
        try {
            if (null == patent) {
                generalResult.setStatus(1);
                generalResult.setMsg("patent为空，无法更改");
                return generalResult;
            }
            if (null == patent.getPatentId() || "".equals(patent.getPatentId()) || patent.getPatentId().length() != 32) {
                generalResult.setStatus(1);
                generalResult.setMsg("id有误，无法更改");
                return generalResult;
            }
            /*未校验  报错！！*/
            if(null != patent.getPatentSchedule() && patent.getPatentSchedule().equals("未通过")){
                patent.setPatentSchedule("未审核");
            }
            int i = tbPatentService.updatePatent(patent);
            if (i <= 0) {
                generalResult.setStatus(1);
                generalResult.setMsg("更新失败");
                return generalResult;
            }
            generalResult.setStatus(0);
            generalResult.setMsg("更新成功");
        } catch (ServiceException e) {
            generalResult.setStatus(1);
            generalResult.setMsg(e.getMessage());
            return generalResult;
        }
        return generalResult;
    }

    @GetMapping("/jbook/{patentId}")
    public GeneralResult jbook(@PathVariable("patentId") String patentId) {
        GeneralResult generalResult = new GeneralResult();
        try {
            if (null == patentId || "".equals(patentId)) {
                generalResult.setStatus(1);
                generalResult.setMsg("id为空，无法查询");
                return generalResult;
            }
            List<String> urlList = tbPatentService.JbookUrlList(patentId);
            if (null == urlList) {
                generalResult.setStatus(1);
                generalResult.setMsg("未找到交底书");
                return generalResult;
            }
            generalResult.setStatus(0);
            generalResult.setMsg("查询成功");
            generalResult.setData(urlList);
        } catch (ServiceException e) {
            generalResult.setStatus(1);
            generalResult.setMsg(e.getMessage());
            return generalResult;
        }
        return generalResult;
    }
    @PostMapping("/submit")
    public GeneralResult submitPatent(@RequestBody Patent patent){
        int flag = tbPatentService.submitPatent(patent);
        if(flag != 0){
            return GeneralResult.build(StatusEnum.Success);
        }else {
            return GeneralResult.build(StatusEnum.Submit_FAIL);
        }

    }

    @PostMapping("/noSubmit")
    public GeneralResult noSubmitPatent(@RequestBody Patent patent){
        int flag = tbPatentService.noSubmitPatent(patent);
        if(flag != 0){
            return GeneralResult.build(StatusEnum.Success);
        }else {
            return GeneralResult.build(StatusEnum.Submit_FAIL);
        }
    }

    @GetMapping("/getMaintainList")
    public GeneralResult getMaintainList() {
        GeneralResult generalResult = new GeneralResult();
        try {
            List<Map<String, String>> maintainList = tbPatentService.getMaintainList();
            if (null == maintainList || maintainList.isEmpty()) {
                generalResult.setStatus(1);
                generalResult.setMsg("未找到下拉列表");
                return generalResult;
            }
            generalResult.setStatus(0);
            generalResult.setMsg("查询成功");
            generalResult.setData(maintainList);
        } catch (ServiceException e) {
            generalResult.setStatus(1);
            generalResult.setMsg(e.getMessage());
            return generalResult;
        }
        return generalResult;
    }

    @PostMapping("/getFileURL")
    public GeneralResult getFileUrlByPatentId(@RequestBody @Valid PatentFileMaintain patentFileMaintain, BindingResult bindingResult) {
        GeneralResult generalResult = new GeneralResult();
        try {
            if(bindingResult.hasErrors()) {
                throw new ServiceException(PatentException.ERROR_PARAME);
            }
            List<PatentFileMaintain> fileUrlByPatentId = tbPatentService.getFileUrlByPatentId(patentFileMaintain);
            generalResult.setStatus(0);
            generalResult.setMsg("查询成功");
            generalResult.setData(fileUrlByPatentId);
        } catch (ServiceException e) {
            generalResult.setStatus(1);
            generalResult.setMsg(e.getMessage());
            return generalResult;
        }
        return generalResult;
    }

    @PostMapping("/upload")
    public GeneralResult uploadFile(HttpServletRequest request) {
        GeneralResult generalResult = new GeneralResult();
        try {

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("fileName");
            if (null == file || file.isEmpty()) {
                return GeneralResult.build(1,"文件为空");
            }
            String fileName = file.getOriginalFilename();
            int i = tbPatentService.uploadFile(request , fileName);
            if (i <= 0) {
                generalResult.setStatus(1);
                generalResult.setMsg("上传失败");
            }
            generalResult.setStatus(0);
            generalResult.setMsg("上传成功");
        } catch (ServiceException e) {
            generalResult.setStatus(1);
            generalResult.setMsg(e.getMessage());
            return generalResult;
        } catch (IOException e) {
            generalResult.setStatus(1);
            generalResult.setMsg(e.getMessage());
            return generalResult;
        }
        return generalResult;
    }

    @PostMapping("/getStatus")
    public GeneralResult getStatus(@RequestBody  Patent patent){
        String status = tbPatentService.getStatus(patent);
        if(status != null || !status.equals("")){
            return GeneralResult.build(0,"成功",status,200);
        }else {
            return GeneralResult.build(StatusEnum.Submit_FAIL,2);
        }
    }

}