package com.sxp.patMag.dao;

import com.sxp.patMag.entity.Indicator;
import com.sxp.patMag.entity.IndicatorExport;
import com.sxp.patMag.entity.Patent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.dao
 * @ClassName: IndicatorMapper
 * @date 2019/11/19 18:48
 */
@Mapper
@Repository
public interface IndicatorMapper {


    /**
     * 新增指标
     * @param list 新增的指标
     * @return 影响行数
     */
    int addIndicator(@Param("list")List<Indicator> list);

}
