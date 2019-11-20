package com.sxp.patMag.service.serviceImpl;

import com.sxp.patMag.dao.PatentSelcetMapper;
import com.sxp.patMag.dao.PatentSelectOrderByTimeMapper;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.PatentSelectOrderByTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mrs.Wang
 * @create 2019-11-20 14:57
 */
@Service
public class PatentSelectOrderByTimeServiceImpl implements PatentSelectOrderByTimeService {
    @Autowired
    PatentSelectOrderByTimeMapper patentSelectOrderByTimeMapper;



    @Override
    public List<Patent> selectPatentByPatentOrderbyTime(Patent patent) {
        return patentSelectOrderByTimeMapper.selectPatentByPatentOrderbyTime(patent);
    }


    @Override
    public List<Patent> selectPatentToAdmin( ) {
        return patentSelectOrderByTimeMapper.selectPatentToAdmin();
    }
}
