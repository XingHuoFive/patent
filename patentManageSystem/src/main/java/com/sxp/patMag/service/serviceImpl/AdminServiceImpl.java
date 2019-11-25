package com.sxp.patMag.service.serviceImpl;

import com.sxp.patMag.dao.AdminMapper;
import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.AdminService;
import com.sxp.patMag.util.GeneralResult;
import com.sxp.patMag.util.WeLogFile;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author： Guofengzhang
 * Date:2019/11/20
 * Time:9:37
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    /**
     * 审核新建的专利
     * @param patent 专利
     * @return 修改结果
     */
    @Override
    public GeneralResult checkPatent(Patent patent) {
        System.out.println(patent);
        String patentSpare = patent.getSpare();
        if (patent == null || patentSpare == null || patentSpare.trim().length() > 1) {
            return GeneralResult.build(1, "点击无效");
        }
        boolean check = true;
        for (int i = 0; i < patentSpare.length(); i++) {
            check = Character.isDigit(patentSpare.charAt(i));
            if (check == false) {
                return GeneralResult.build(1, "点击无效");
            }
        }
        // 如果该专利通过审核，并且是还没有被认领，就把他的进度修改成待认领状态
        if (Integer.parseInt(patentSpare) == 1 && (Integer.parseInt(patent.getPatentClaim()) == 0 || patent.getPatentClaim() == null)) {
            patent.setPatentSchedule("2");
        }

        // 如果该专利通过审核，并且已经被认领了，就把他的进度修改成已提交状态
        if (Integer.parseInt(patentSpare) == 1 && Integer.parseInt(patent.getPatentClaim()) == 1) {
            patent.setPatentSchedule("5");
        }
        if (Integer.parseInt(patentSpare) == 0 && (Integer.parseInt(patent.getPatentClaim()) == 0 || patent.getPatentClaim() == null)) {
            patent.setPatentSchedule("3");
        }
        // 如果该专利审核没通过，就将它的进度修改成未通过
        if (Integer.parseInt(patentSpare) == 0 && Integer.parseInt(patent.getPatentClaim()) == 1) {
            patent.setPatentSchedule("3");
        }

        boolean flag = adminMapper.checkPatent(patent);
        String spareNum = adminMapper.selectSpareOfPatent(patent.getPatentId());
        if (spareNum == null) {
            spareNum = "0";
        }
        if (flag == true && Integer.parseInt(spareNum) == 1) {
            return GeneralResult.build(0, "审核通过!");
        }
        if (flag == true && Integer.parseInt(spareNum) == 0){
            return GeneralResult.build(1, "审核不通过!");
        }
        return GeneralResult.build(1, "出现错误!");
    }

    /**
     * 根据专利id查询它所有的文件
     * @param patentId 专利id
     * @return 文件地址
     */
    @Override
    public GeneralResult selectAllFilesByPatentId(String patentId) {
        if (patentId == null || patentId.length() > 16) {
            return GeneralResult.build(1, "该专利id无效");
        }
        List<Jbook> list = adminMapper.selectAllFilesByPatentId(patentId);
        if (list == null || list.size() == 0) {
            //返回登录失败
            return GeneralResult.build(1, "fail");
        }
        return GeneralResult.build(0, "success", list);
    }

    /**
     *  管理员读取日志
     * @return 日志列表
     */
    @Override
    public GeneralResult readLogFile(String pageNumber) {
        List<String> list = WeLogFile.readLog();
        if (list == null || list.size() == 0) {
            //返回查询失败
            return GeneralResult.build(1, "fail");
        }
        if (pageNumber == null || pageNumber.length() == 0) {
            return GeneralResult.build(0, "success", list);
        }
        return GeneralResult.build(0, "success", list.get(Integer.parseInt(pageNumber)-1));
    }
}
