package com.sxp.patMag.service;

import com.sxp.patMag.entity.Indicator;
import com.sxp.patMag.entity.IndicatorExport;
import com.sxp.patMag.entity.Patent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.dao
 * @ClassName: IndicatorMapper
 * @date 2019/11/19 18:48
 */
public interface IndicatorService {

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
     * 新增指标
     * @param indicator 新增的指标
     * @return 影响行数
     */
    int save(Indicator indicator);

    /**
     * 导出成excel
     * @param indicatorExport 导出条件
     * @return 是否导出成功
     */
    boolean export(IndicatorExport indicatorExport, HttpServletResponse resp, HttpServletRequest req);

    /**
     * 根据id获取指标详情
     * @param indicatorId 要获取的指标id
     * @return 获取到的指标详情
     */
    IndicatorExport getById(String indicatorId);
}
