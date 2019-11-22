package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.AdminService;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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

    /** 审核新建的专利 */
    @RequestMapping(value = "/checkPatent", method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult checkPatent(@RequestBody Patent patent) {
        return adminService.checkPatent(patent);
    }

    /** 根据专利的id查询专利所属的交底书 */
    @RequestMapping(value = "/selectAllFilesByPatentId", method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult selectAllFilesByPatentId(String patentId) {
        return adminService.selectAllFilesByPatentId(patentId);
    }

    /** 管理员读取日志 */
    @RequestMapping(value = "/readLogFile", method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult readLogFile(String pageNumber) {
        return adminService.readLogFile(pageNumber);
    }

}
