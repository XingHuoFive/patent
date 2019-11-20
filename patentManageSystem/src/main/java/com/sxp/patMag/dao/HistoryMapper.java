package com.sxp.patMag.dao;


import com.sxp.patMag.entity.History;
import com.sxp.patMag.entity.Jbook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HistoryMapper {


   Integer insertHistory(History history);

}