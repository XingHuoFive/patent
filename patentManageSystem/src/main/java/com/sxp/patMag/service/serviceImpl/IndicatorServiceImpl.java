package com.sxp.patMag.service.serviceImpl;

import com.sxp.patMag.dao.IndicatorMapper;
import com.sxp.patMag.entity.Indicator;
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
    public List<Indicator> list() {
        return indicatorMapper.getIndicatorList();
    }

    @Override
    public List<Indicator> listByPatentId(String patentId) {
        return indicatorMapper.getIndicatorListByPatentId(patentId);
    }

    @Override
    public int save(Indicator indicator) {
        return indicatorMapper.addIndicator(indicator);
    }
}
