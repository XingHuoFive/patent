package com.sxp.patMag.service.serviceImpl;

import com.sxp.patMag.dao.IndicatorMapper;
import com.sxp.patMag.entity.Indicator;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.service.serviceImpl
 * @ClassName: IndicatorServiceImpl
 * @date 2019/11/19 19:23
 */
@Service
public class IndicatorServiceImpl implements IndicatorService {

    @Autowired
    private IndicatorMapper indicatorMapper;

    @Override
    public List<Patent> list() {
        return indicatorMapper.getPatentList();
    }

    @Override
    public List<Patent> listByPatentId(Patent patent) {
        return indicatorMapper.getPatentListByVO(patent);
    }

    @Override
    public int save(Indicator indicator) {
        return indicatorMapper.addIndicator(indicator);
    }

    @Override
    public void export(List<Patent> list) {

    }
}
