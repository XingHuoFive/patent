package com.sxp.patMag.dao;


import com.sxp.patMag.entity.TbPatent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbPatentMapper {

    TbPatent selectById(String patentId);

}