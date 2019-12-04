package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.service.AdminService;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author： Guofengzhang
 * Date:2019/11/20
 * Time:9:32
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    /**
     * 审核新建的专利
     */
    @RequestMapping(value = "/checkPatent", method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult checkPatent(@RequestBody Patent patent) {
        return adminService.checkPatent(patent);
    }

    /**
     * 管理员读取日志
     */
    @RequestMapping(value = "/readLogFile/{role}", method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult readLogFile(@PathVariable("role") String role, HttpServletResponse response) {
        return adminService.readLogFile(role, response);
    }

    /**
     * 修改专利的备注状态，模拟通知
     *
     * @param patent 专利
     * @return 修改结果
     */
    @RequestMapping(value = "/updatePatentRemarkView", method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult updatePatentRemarkView(@Valid @RequestBody Patent patent) {
        return adminService.updatePatentRemarkView(patent);
    }

    /**
     * 登录后显示通知
     *
     * @param user 登录者信息
     * @return 通知列表
     */
    @RequestMapping(value = "/showPatentNotice", method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult showPatentNotice(@Valid @RequestBody User user) {
        return adminService.showPatentNotice(user);
    }

//    @RequestMapping(value = "/getLogPath/{role}", method = RequestMethod.POST)
//    @ResponseBody
//    public GeneralResult getLogPath(@PathVariable("role") String role) {
//        return adminService.getLogPath(role);
//    }
}
