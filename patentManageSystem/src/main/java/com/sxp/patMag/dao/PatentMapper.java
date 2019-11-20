package com.sxp.patMag.dao;


import com.sxp.patMag.entity.IndicatorExport;
import com.sxp.patMag.entity.Patent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 * Author： Jude
 * Date:2019/11/19
 * Time:18:18
 */
@Mapper
public interface PatentMapper {

    /**
     * 获取所有的指标
     * @return 指标列表
     */
    List<IndicatorExport> getPatentList();

    /**
     * 复杂查询
     * @param indicatorExport 要查询的字段
     * @return 查询出的结果
     */
    List<IndicatorExport> getPatentListByVO(IndicatorExport indicatorExport);

    List<Patent> selectById(String patentId);

}