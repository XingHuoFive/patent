package com.sxp.patMag.dao;


import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.entity.User;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UploadMapper {


   Integer insertJbook(Jbook jbook);
 Integer updateJbookStatusByPatentId(String  patentId);


}