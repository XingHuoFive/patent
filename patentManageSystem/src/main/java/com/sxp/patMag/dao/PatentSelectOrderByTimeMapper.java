package com.sxp.patMag.dao;

import com.sxp.patMag.entity.Patent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Mrs.Wang
 * @create 2019-11-20 14:49
 */
@Mapper
@Repository
public interface PatentSelectOrderByTimeMapper {
    List<Patent> selectPatentByPatentOrderbyTime(Patent patent);
    List<Patent> selectPatentToAdmin();
}
