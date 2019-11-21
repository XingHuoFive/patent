package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.AdminService;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author： Guofengzhang
 * Date:2019/11/20
 * Time:9:32
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/checkPatent", method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult checkPatent(String patentId, String updateField) {
        return adminService.checkPatent(patentId, updateField);
    }

    @RequestMapping(value = "/selectAllFilesByPatentId", method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult selectAllFilesByPatentId(String patentId) {
        return adminService.selectAllFilesByPatentId(patentId);
    }

    @RequestMapping(value = "/readLogFile", method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult readLogFile() {
        return adminService.readLogFile();
    }
}
