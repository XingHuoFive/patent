package com.sxp.patMag.service.serviceImpl;


import com.sxp.patMag.dao.TbPatentMapper;
import com.sxp.patMag.entity.TbPatent;
import com.sxp.patMag.service.TbPatentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TbPatentServiceImpl implements TbPatentService {

    @Resource
    private TbPatentMapper tbPatentMapper;

    public TbPatent selectById(String patentId) {

        return tbPatentMapper.selectById(patentId);
    }



}
