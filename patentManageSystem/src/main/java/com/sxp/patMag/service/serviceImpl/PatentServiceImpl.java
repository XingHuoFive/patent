package com.sxp.patMag.service.serviceImpl;


import com.sxp.patMag.dao.PatentMapper;
import com.sxp.patMag.entity.IndicatorExport;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.PatentService;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * Author： Jude
 * Date:2019/11/19
 * Time:18:18
 */
@Service
public class PatentServiceImpl implements PatentService {

    @Resource
    private PatentMapper tbPatentMapper;

    @Override
    public GeneralResult selectById(String patentId) {

          List<Patent> patentList    = tbPatentMapper.selectById(patentId);
          return  GeneralResult.build(0,"success",patentList);


    }

    @Override
    public List<IndicatorExport> list() {
        return tbPatentMapper.getPatentList();
    }

    @Override
    public List<IndicatorExport> listByPatent(IndicatorExport indicatorExport) {
        return tbPatentMapper.getPatentListByVO(indicatorExport);
    }

    @Override
    public IndicatorExport getById(String indicatorId) {
        return tbPatentMapper.getIndicatorById(indicatorId);
    }

}
