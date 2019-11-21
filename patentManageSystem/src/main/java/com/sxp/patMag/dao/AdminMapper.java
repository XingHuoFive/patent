package com.sxp.patMag.dao;

import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.entity.Patent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {
    boolean checkPatent(Patent patent);

    List<Jbook> selectAllFilesByPatentId(@Param("patentId") String patentId);

}
