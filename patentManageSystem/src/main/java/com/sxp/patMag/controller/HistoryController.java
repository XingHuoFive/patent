package com.sxp.patMag.controller;

import com.sxp.patMag.entity.History;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.HistoryService;
import com.sxp.patMag.service.UploadService;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Author： Jude
 * Date:2019/11/21
 * Time:9:36
 */

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService ;
    /**
     * 实现文件上传
     *
     * */


    @RequestMapping(value = "/getHistory", method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult getHistory(@RequestBody @Valid Patent patent , BindingResult bindingResult ){

        if(bindingResult.hasErrors()){
            return GeneralResult.build(1,bindingResult.getFieldError().getDefaultMessage());
        }

           return  historyService.selectHistory(patent);
    }
}
