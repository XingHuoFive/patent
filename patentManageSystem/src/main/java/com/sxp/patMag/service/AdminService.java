package com.sxp.patMag.service;


import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.util.GeneralResult;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author： Guofengzhang
 * Date:2019/11/20
 * Time:9:37
 */
public interface AdminService {
    /**
     * 审核新建的专利
     *
     * @param patent 专利
     * @return 修改结果
     */
    GeneralResult checkPatent(Patent patent);

    /**
     * 读取日志
     *
     * @return 封装数据包
     */
    GeneralResult readLogFile(String role, HttpServletResponse response);

//    GeneralResult getLogPath(String role);

    /**
     * 修改备注
     * @param patent 存储修改信息
     * @return
     */
    GeneralResult updatePatentRemarkView(Patent patent);

    /**
     * 登录后显示通知
     * @param user 登录者信息
     * @return 通知列表
     */
    GeneralResult showPatentNotice(User user);
}
