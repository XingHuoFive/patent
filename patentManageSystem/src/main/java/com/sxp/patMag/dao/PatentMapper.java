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

    /**
     * 根据id查询专利
     * @param patentId 专利id
     * @return 查询到的专利
     */
    Patent getById(String patentId);

    /**
     * 管理员根据专利id查询所有交底书
     * @param patentId 要查询的专利id
     * @return 查询到的交底书
     */
    List<String> getJbookURLList(String patentId);


    /**
     * 提交专利
     * @param patent
     * @return
     */
    int submitPatent(Patent patent);
}