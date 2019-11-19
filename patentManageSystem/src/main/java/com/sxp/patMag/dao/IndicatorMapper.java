package com.sxp.patMag.dao;

import com.sxp.patMag.entity.Indicator;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.dao
 * @ClassName: IndicatorMapper
 * @date 2019/11/19 18:48
 */
@Mapper
public interface IndicatorMapper {

    /**
     * 获取所有的指标
     * @return 指标列表
     */
    List<Indicator> getIndicatorList();

    /**
     * 根据专利id获取指标
     * @param patentId 要获取指标的id
     * @return 获取到的指标列表
     */
    List<Indicator> getIndicatorListByPatentId(String patentId);

}
