package com.sxp.patMag.service.serviceImpl;

import com.sxp.patMag.annotation.Monitor;
import com.sxp.patMag.dao.AdminMapper;
import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.exception.PatentException;
import com.sxp.patMag.exception.ServiceException;
import com.sxp.patMag.service.AdminService;
import com.sxp.patMag.util.GeneralResult;
import com.sxp.patMag.util.WeLogFile;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author： Guofengzhang
 * Date:2019/11/20
 * Time:9:37
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

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
        if (patentSpareInt == 0 && patentClaimInt == 0) {
            patent.setPatentSchedule("未通过");
        }
        // 如果该专利审核没通过，就将它的进度修改成未通过
        if (patentSpareInt == 0 && patentClaimInt == 1) {
            patent.setPatentSchedule("编写中");
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
     * 根据专利id查询它所有的文件
     *
     * @param patentId 专利id
     * @return 文件地址
     */
    @Override
    public GeneralResult selectAllFilesByPatentId(String patentId) {
        if (patentId == null || patentId.length() > 16) {
            return GeneralResult.build(1, "该专利id无效");
        }
        boolean check = true;
        for (int i = 0; i < patentId.length(); i++) {
            check = Character.isDigit(patentId.charAt(i));
            if (check == false) {
                return GeneralResult.build(1, "校验出错");
            }
        }
        List<Jbook> list = adminMapper.selectAllFilesByPatentId(patentId);
        if (list == null || list.size() == 0) {
            //返回登录失败
            return GeneralResult.build(1, "fail");
        }
        return GeneralResult.build(0, "success", list);
    }

    /**
     * 管理员读取日志
     *
     * @return 日志列表
     */
    @Override
    public GeneralResult readLogFile(String role, HttpServletResponse response) {
        String dir = WeLogFile.readLog();
        if (dir == null || dir.length() == 0) {
            //返回查询失败
            return GeneralResult.build(1, "fail", "查询失败");
        }
        if ("0".equals(role)) {
            return GeneralResult.build(1, "fail", "您不是管理员，无法查看日志!");
        }
        if ("1".equals(role)) {
            File file = new File(dir);
            if (file.exists()) {
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                response.setCharacterEncoding("utf-8");
                response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("下载成功!");
                    return GeneralResult.build(0, "success", "下载成功!");
                } catch (IOException e) {
                    throw new ServiceException(PatentException.EXPORT_ERROR);
                }finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return GeneralResult.build(0, "fail", "下载失败");
        }
        return GeneralResult.build(0, "success", "role没收到!");
    }
}
