package com.sxp.patMag.service;

import com.sxp.patMag.entity.IndicatorExport;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.PatentFileMaintain;
import com.sxp.patMag.entity.PatentMaintain;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.service
 * @ClassName: PatentService
 * @date 2019/11/26 17:08
 */
public interface PatentService {

    /**
     * 根据id查询专利
     * @param patentId id
     * @return 查询到的专利
     */
    Patent selectById(String patentId);

    /**
     * 获取所有的指标
     * @return 指标列表
     */
    List<IndicatorExport> list();

    /**
     * 根据查询条件获取指标
     * @param indicatorExport 要获取指标查询条件
     * @return 获取到的指标列表
     */
    List<IndicatorExport> listByPatent(IndicatorExport indicatorExport);

    /**
     * 根据id获取指标详情
     * @param indicatorId 要获取的指标id
     * @return 获取到的指标详情
     */

    IndicatorExport getById(String indicatorId);




    /**
     * 更改专利信息
     * @param patent 待更改的信息
     * @return 影响条数
     */
    int updatePatent(Patent patent);

    /**
     * 管理员获取交底书下载链接
     * @return 交底书下载链接
     */
    List<String> JbookUrlList(String patentId);

    /**
     * 提交专利
     * @param patent
     * @return
     */
    int submitPatent(Patent patent);

    /**
     * 驳回提交的专利
     * @param patent
     * @return
     */
    int noSubmitPatent(Patent patent);

    /**
     * 获取数据维护阶段的字典值
     * @return 字典值列表
     */
    List<Map<String, String>> getMaintainList();

    /**
     * 根据专利id查询文件下载链接
     * @return 下载链及对应文件类型
     */
    List<PatentFileMaintain> getFileUrlByPatentId(PatentFileMaintain patentFileMaintain);

    /**
     * 上传文件维护阶段文件
     * @param request 获取到的内容
     * @return 影响行数
     */
    int uploadFile(HttpServletRequest request, String fileName) throws IOException;


    /**
     * 查询进度（进度条）
     * @param patent
     * @return
     */
    String getStatus(Patent patent);
}
