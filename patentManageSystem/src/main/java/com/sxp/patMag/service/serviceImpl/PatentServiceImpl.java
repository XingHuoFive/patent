package com.sxp.patMag.service.serviceImpl;


import com.sxp.patMag.dao.PatentMapper;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.PatentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PatentServiceImpl implements PatentService {

    @Resource
    private PatentMapper tbPatentMapper;

    public Patent selectById(String patentId) {

        return tbPatentMapper.selectById(patentId);
    }



}
