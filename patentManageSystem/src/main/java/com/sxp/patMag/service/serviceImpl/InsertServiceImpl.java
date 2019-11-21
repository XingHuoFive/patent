package com.sxp.patMag.service.serviceImpl;

import com.sxp.patMag.dao.InsertMapper;
import com.sxp.patMag.dao.PatentMapper;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.InsertService;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Mrs.Wang
 * @create 2019-11-19 18:59
 */
@Service
public class InsertServiceImpl implements InsertService {
    @Resource
    private InsertMapper insertMapper;


    public GeneralResult InsertPatent(Patent patent){
        System.out.println("______________");
        int a = insertMapper.InsertPatent(patent);
        System.out.println("______________");

        if(a>0){
            return GeneralResult.build(0,"success");
        }
        return GeneralResult.build(1,"failed");

    }

}