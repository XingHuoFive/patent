package com.sxp.patMag.service.serviceImpl;

import com.sxp.patMag.dao.PatentSelcetMapper;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.PatentSelcetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PatentSelcetServiceImpl implements PatentSelcetService {


    @Autowired
    PatentSelcetMapper  patentSelcetMapper;


    @Override
    public List<Patent> selectPatentByPatent(Patent patent) {
        return patentSelcetMapper.selectPatentByPatent(patent);
    }


    @Override
    public List<Patent> selectPatentToUser( ) {
        return patentSelcetMapper.selectPatentToUser();
    }

    @Override
    public Patent selectPatentById(String patentId){
        return patentSelcetMapper.selectPatentById( patentId);
    }

    @Override
    public Integer updatePatentToWritePerson(Patent patent) {
        return patentSelcetMapper.updatePatentToWritePerson(patent);
    }
}
