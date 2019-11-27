package com.sxp.patMag.dao;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Mrs.Wang
 * @create 2019-11-19 18:49
 */
@Mapper
public interface InsertMapper {

    Integer insertPatent(Patent patent);

}
