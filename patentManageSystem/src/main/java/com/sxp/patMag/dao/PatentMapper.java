package com.sxp.patMag.dao;


import com.sxp.patMag.entity.Patent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 * Author： Jude
 * Date:2019/11/19
 * Time:18:18
 */
@Mapper
public interface PatentMapper {

    List<Patent> selectById(String patentId);

}