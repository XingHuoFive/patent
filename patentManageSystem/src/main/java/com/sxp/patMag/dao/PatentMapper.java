package com.sxp.patMag.dao;


import com.sxp.patMag.entity.IndicatorExport;
import com.sxp.patMag.entity.Patent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author lhx
 * @PackageName: com.sxp.patMag.dao
 * @ClassName: PatentMapper
 * @date 2019/11/26 17:08
 */
@Mapper
@Repository
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

    /**
     * 根据id获取指标详情
     * @param indicatorId 要获取的指标id
     * @return 获取到的指标详情
     */
    IndicatorExport getIndicatorById(String indicatorId);

    /**
     * 更改专利信息
     * @param patent 待更改的信息
     * @return 影响条数
     */
    int updatePatent(Patent patent);

}