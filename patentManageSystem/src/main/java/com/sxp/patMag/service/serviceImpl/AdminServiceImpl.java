package com.sxp.patMag.service.serviceImpl;

import com.sxp.patMag.dao.AdminMapper;
import com.sxp.patMag.entity.JBook;
import com.sxp.patMag.service.AdminService;
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
    public boolean checkPatent(String patentId, String updateField) {
        return adminMapper.checkPatent(patentId, updateField);
    }

    /**
     * 根据专利id查询它所有的文件
     * @param patentId 专利id
     * @return 文件地址
     */

    @Override
    public List<JBook> selectAllFilesByPatentId(String patentId) {
        return adminMapper.selectAllFilesByPatentId(patentId);
    }

    /**
     *  管理员读取日志
     * @return 日志列表
     */
    @Override
    public List<String> readLogFile() {
        return WeLogFile.readLog();
    }
}
