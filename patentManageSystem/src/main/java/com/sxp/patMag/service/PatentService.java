package com.sxp.patMag.service;

import com.sxp.patMag.entity.IndicatorExport;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.util.GeneralResult;

import java.util.List;

/**
 * Author： Jude
 * Date:2019/11/19
 * Time:18:18
 */
public interface PatentService {

    public GeneralResult selectById(String patentId);

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

}
