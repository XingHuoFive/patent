package com.sxp.patMag.dao;


import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.entity.User;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UploadMapper {

    /**
     * 添加交底书
     * @param jbook
     * @return
     */
   Integer insertJbook(Jbook jbook);

    /**
     *
     * @param patentId
     * @return
     */
 Integer updateJbookStatusByPatentId(String  patentId);

    /**
     *  修改进度
     * @param patentId 专利id
     * @return
     */
    Integer updatePatentSchedule(String patentId);


}