package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.PatentFileMaintain;
import com.sxp.patMag.exception.PatentException;
import com.sxp.patMag.exception.ServiceException;
import com.sxp.patMag.service.PatentService;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

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
            List<String> urlList = tbPatentService.JbookURLList(patentId);
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
            return GeneralResult.build(0,"成功");
        }else {
            return GeneralResult.build(1,"提交失败");
        }

    }

    @PostMapping("/noSubmit")
    public GeneralResult noSubmitPatent(@RequestBody Patent patent){
        int flag = tbPatentService.noSubmitPatent(patent);
        if(flag != 0){
            return GeneralResult.build(0,"成功");
        }else {
            return GeneralResult.build(1,"提交失败");
        }
    }

    @GetMapping("/getMaintainList")
    public GeneralResult getMaintainList() {
        GeneralResult generalResult = new GeneralResult();
        try {
            List<String> maintainList = tbPatentService.getMaintainList();
            if (null == maintainList) {
                generalResult.setStatus(1);
                generalResult.setMsg("未找到下拉列表");
                return generalResult;
            }
            generalResult.setStatus(0);
            generalResult.setMsg("查询成功");
            generalResult.setData(maintainList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generalResult;
    }

    @PostMapping("/getFileURL")
    public GeneralResult getFileURLByPatentId(@RequestBody @Valid PatentFileMaintain patentFileMaintain, BindingResult bindingResult) {
        GeneralResult generalResult = new GeneralResult();
        try {
            if(bindingResult.hasErrors()) {
                throw new ServiceException(PatentException.ERROR_PARAME);
            }
            List<PatentFileMaintain> fileURLByPatentId = tbPatentService.getFileURLByPatentId(patentFileMaintain);
            generalResult.setStatus(0);
            generalResult.setMsg("查询成功");
            generalResult.setData(fileURLByPatentId);
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
            int i = tbPatentService.uploadFile(request);
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

}