package com.sxp.patMag.service;


import com.sxp.patMag.entity.Patent;
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
     * 根据专利id查询它所有的文件
     *
     * @param patentId 专利id
     * @return 文件地址
     */
    GeneralResult selectAllFilesByPatentId(String patentId);

    /**
     * 读取日志
     *
     * @return 封装数据包
     */
    GeneralResult readLogFile(String role, HttpServletResponse response);
}
