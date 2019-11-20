package com.sxp.patMag.dao;

import com.sxp.patMag.entity.JBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {
    boolean checkPatent(@Param("patentId")String patentId, @Param("updateField")String updateField);

    List<JBook> selectAllFilesByPatentId(@Param("patentId")String patentId);
}
