package com.sxp.patMag.dao;

import com.sxp.patMag.entity.Indicator;
import com.sxp.patMag.entity.Patent;
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
     * 新增指标
     * @param indicator 新增的指标
     * @return 影响行数
     */
    int addIndicator(Indicator indicator);

    /**
     * 获取指标列表
     * @return 指标列表
     */
    List<Patent> getPatentList();

    /**
     * 复杂查询
     * @param patent 要查询的字段
     * @return 查询出的结果
     */
    List<Patent> getPatentListByVO(Patent patent);

}
