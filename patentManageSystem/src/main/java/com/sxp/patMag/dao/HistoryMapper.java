package com.sxp.patMag.dao;


import com.sxp.patMag.entity.History;
import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.util.GeneralResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistoryMapper {


   Integer insertHistory(History history);
   List<History> selectHistory(String patnetId);
}