package com.sxp.patMag.dao;


import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoginMapper {

    List<User> CheckUser(User user);


}