package com.sxp.patMag.service;

import com.sxp.patMag.entity.Indicator;
import com.sxp.patMag.entity.Patent;
import org.springframework.stereotype.Service;

import java.util.List;

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
    List<Patent> list();

    /**
     * 根据查询条件获取指标
     * @param patent 要获取指标查询条件
     * @return 获取到的指标列表
     */
    List<Patent> listByPatentId(Patent patent);

    /**
     * 新增指标
     * @param indicator 新增的指标
     * @return 影响行数
     */
    int save(Indicator indicator);

}
