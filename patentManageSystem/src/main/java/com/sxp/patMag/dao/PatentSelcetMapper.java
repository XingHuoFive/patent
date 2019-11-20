package com.sxp.patMag.dao;

import com.sxp.patMag.entity.Patent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface PatentSelcetMapper {
    List<Patent> selectPatentByPatent(Patent patent);
}
