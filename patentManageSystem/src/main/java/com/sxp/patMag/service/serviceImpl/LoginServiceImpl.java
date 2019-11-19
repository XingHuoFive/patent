package com.sxp.patMag.service.serviceImpl;


import com.sxp.patMag.dao.LoginMapper;
import com.sxp.patMag.dao.PatentMapper;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.service.LoginService;
import com.sxp.patMag.service.PatentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService  {

    @Resource
    private LoginMapper loginMapper;

    public Integer CheckUser(User user) {

        return loginMapper.CheckUser(user);
    }



}
