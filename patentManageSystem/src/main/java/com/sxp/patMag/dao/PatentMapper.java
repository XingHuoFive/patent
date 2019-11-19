package com.sxp.patMag.dao;


import com.sxp.patMag.entity.Patent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PatentMapper {

    Patent selectById(String patentId);

}