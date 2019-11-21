package com.sxp.patMag.service;

import com.sxp.patMag.entity.Patent;

import java.util.List;

public interface PatentSelcetService {
    List<Patent> selectPatentByPatent(Patent patent);
    List<Patent> selectPatentToUser();
    Patent selectPatentById(String patentId);
    Integer updatePatentToWritePerson(Patent patent);
}
