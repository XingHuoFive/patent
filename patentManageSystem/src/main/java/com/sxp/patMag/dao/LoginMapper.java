package com.sxp.patMag.dao;


import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoginMapper {

    /**
     *
     * @param user
     * @return 校验密码
     */
    List<User> checkUser(User user);

    /**
     *
     * @param userId
     * @return 根据用户id查询user
     */
    List<User> selectUserById(String  userId);

}