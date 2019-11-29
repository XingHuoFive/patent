package com.sxp.patMag.service.serviceImpl;


import com.sxp.patMag.dao.PatentMapper;
import com.sxp.patMag.entity.IndicatorExport;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.PatentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.serviceImpl
 * @ClassName: PatentServiceImpl
 * @date 2019/11/26 17:08
 */
@Service
public class PatentServiceImpl implements PatentService {

    @Resource
    private PatentMapper tbPatentMapper;

    @Override
    public Patent selectById(String patentId) {
        return tbPatentMapper.getById(patentId);
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

    @Override
    public int updatePatent(Patent patent) {
        return tbPatentMapper.updatePatent(patent);
    }

    @Override
    public List<String> JbookURLList(String patentId) {
        return tbPatentMapper.getJbookURLList(patentId);
    }

    @Override
    public int submitPatent(Patent patent) {
        return tbPatentMapper.submitPatent(patent);
    }
}
