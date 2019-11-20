package com.sxp.patMag.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {
    boolean checkPatent(@Param("patentId")String patentId, @Param("updateField")String updateField);

    List<String> selectAllFilesByPatentId(@Param("patentId")String patentId);
}
