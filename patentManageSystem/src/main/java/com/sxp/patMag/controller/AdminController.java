package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
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

    @RequestMapping("/checkPatent")
    public String checkPatent(String patentId, String updateField) {
        boolean flag = adminService.checkPatent(patentId, updateField);
        if (flag) {
            return "";
        } else {
            return "";
        }
    }

    @RequestMapping("/selectAllFilesByPatentId")
    public String selectAllFilesByPatentId(String patentId) {
        List<String> list = adminService.selectAllFilesByPatentId(patentId);
        return "";
    }
}
