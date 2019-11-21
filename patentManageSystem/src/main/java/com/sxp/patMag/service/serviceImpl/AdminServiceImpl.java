package com.sxp.patMag.service.serviceImpl;

import com.sxp.patMag.dao.AdminMapper;
import com.sxp.patMag.entity.Jbook;
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
     * @param patentId 专利id
     * @param updateField 要修改成的状态
     * @return 修改结果
     */
    @Override
    public GeneralResult checkPatent(String patentId, String updateField) {
        boolean flag = adminMapper.checkPatent(patentId, updateField);
        if (flag) {
            return GeneralResult.build(0, "修改成功");
        } else {
            return GeneralResult.build(1, "修改不成功");
        }
    }

    /**
     * 根据专利id查询它所有的文件
     * @param patentId 专利id
     * @return 文件地址
     */

    @Override
    public GeneralResult selectAllFilesByPatentId(String patentId) {
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
    public GeneralResult readLogFile() {
        List<String> list = WeLogFile.readLog();
        if (list == null || list.size() == 0) {
            //返回查询失败
            return GeneralResult.build(1, "fail");
        }
        return GeneralResult.build(0, "success", list);
    }
}
