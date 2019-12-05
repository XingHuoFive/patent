package com.sxp.patMag.service.serviceImpl;

import com.sxp.patMag.annotation.Monitor;
import com.sxp.patMag.dao.AdminMapper;
import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.entity.LogPo;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.service.AdminService;
import com.sxp.patMag.service.PatentService;
import com.sxp.patMag.util.GeneralResult;
import com.sxp.patMag.util.RedisUtil;
import com.sxp.patMag.util.WeLogFile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author： Guofengzhang
 * Date:2019/11/20
 * Time:9:37
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;
    @Autowired
    private PatentService tbPatentService;
    @Autowired
    private RedisUtil redis;
    /**
     * 审核新建的专利
     *
     * @param patent 专利
     * @return 修改结果
     */
    @Override
    @Monitor("审核")
    public GeneralResult checkPatent(Patent patent) {
        System.out.println(patent);
        if (patent == null) {
            return GeneralResult.build(1, "校验不通过");
        }
        String patentSpare = patent.getSpare();
        String pClaim = patent.getPatentClaim();
        String patentId = patent.getPatentId();
        if (patentId == null) {
            return GeneralResult.build(1, "id为空!");
        }
        if (patentId.length() > 32) {
            return GeneralResult.build(1, "id超长!");
        }
        if (patentSpare == null || patentSpare.length() == 0) {
            return GeneralResult.build(1, "通过字段参数为空");
        }
        if (pClaim == null || pClaim.length() == 0) {
            return GeneralResult.build(1, "认领字段参数为空");
        }
        if (pClaim.length() > 1) {
            return GeneralResult.build(1, "认领字段参数超长");
        }
        if (patentSpare.trim().length() > 1) {
            return GeneralResult.build(1, "通过字段参数超长");
        }
        boolean check = true;
        check = Character.isDigit(patentSpare.charAt(0));
        if (check == false) {
            return GeneralResult.build(1, "参数包含除数字之外的其他字母");
        }
        check = true;
        for (int i = 0; i < pClaim.length(); i++) {
            check = Character.isDigit(pClaim.charAt(i));
            if (check == false) {
                return GeneralResult.build(1, "参数包含除数字之外的其他字母");
            }
        }
        String patentRemarks = patent.getPatentRemarks();
        if (patentRemarks == null || "".equals(patentRemarks)) {
            patent.setPatentRemarks("你的提交中有内容不符合专利局标准!");
        }
        int patentSpareInt = Integer.parseInt(patentSpare);
        int patentClaimInt = Integer.parseInt(pClaim);
        // 如果该专利通过审核，并且是还没有被认领，就把他的进度修改成待认领状态
        if (patentSpareInt == 1 && patentClaimInt == 0) {
            patent.setPatentSchedule("待认领");
        }
        // 如果该专利通过审核，并且已经被认领了，就把他的进度修改成待提交状态
        if (patentSpareInt == 1 && patentClaimInt == 1) {
            patent.setPatentSchedule("待提交");
        }
        // 如果该专利审核没通过，就将它的进度修改成未通过
        if (patentSpareInt == 0 && patentClaimInt == 0) {
            patent.setPatentSchedule("未通过");
        }
        // 如果该专利审核没通过，就将它的进度修改成编写中
        if (patentSpareInt == 0 && patentClaimInt == 1) {
            patent.setPatentSchedule("编写中");
            tbPatentService.noSubmitPatent(patent);
        }


        boolean flag = adminMapper.checkPatent(patent);
        String spareNum = adminMapper.selectSpareOfPatent(patentId);
        int spareNumInt = Integer.parseInt(spareNum);
        if (spareNum == null) {
            spareNum = "0";
        }
        if (flag == true && spareNumInt == 1) {
            return GeneralResult.build(0, "审核通过!");
        }
        if (flag == true && spareNumInt == 0) {
            return GeneralResult.build(1, "审核不通过!");
        }
        return GeneralResult.build(1, "其他错误!");
    }
    /**
     * 管理员读取日志
     *
     * @return 日志列表
     */
   @Override
    public GeneralResult readLogFile(String role, HttpServletResponse response) {
        if ("0".equals(role)) {
            return GeneralResult.build(1, "fail", "您不是管理员，无法查看日志!");
        }
        if ("1".equals(role)) {
           List  logList =  redis.lGet("logs",0,89);
            return GeneralResult.build(0, "success", logList);
        }
            return GeneralResult.build(1, "failed", "您啥也不是");
    }


     /**
     * 修改通知状态
     *
     * @param patent 存储目标专利信息
     * @return 操作信息
     */
    @Override
    public GeneralResult updatePatentRemarkView(Patent patent) {
        int flag = adminMapper.updatePatentRemarkView(patent);
        if (flag > 0) {
            return GeneralResult.build(0, "success", "修改成功");
        } else {
            return GeneralResult.build(1, "fail", "修改失败");
        }
    }
    /**
     * 登录后显示通知
     *
     * @param user 登录者信息
     * @return 通知列表
     */
    @Override
    public GeneralResult showPatentNotice( User user) {
        List<Patent> list = adminMapper.selectRemarkViewOfPatent(user);
        if (list != null || list.size() != 0) {
            return GeneralResult.build(0, "success", list);
        } else {
            return GeneralResult.build(1, "fail", "暂时没有通知");
        }
    }
}
