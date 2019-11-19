package com.sxp.patMag.dao;


import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {

    Integer CheckUser(User user);

}