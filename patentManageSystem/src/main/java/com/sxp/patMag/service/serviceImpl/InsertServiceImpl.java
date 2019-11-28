package com.sxp.patMag.service.serviceImpl;

import com.sxp.patMag.annotation.Monitor;
import com.sxp.patMag.dao.InsertMapper;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.IndicatorService;
import com.sxp.patMag.service.InsertService;
import com.sxp.patMag.util.GeneralResult;
import com.sxp.patMag.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author Mrs.Wang
 * @create 2019-11-19 18:59
 */
@Service
public class InsertServiceImpl implements InsertService {

    @Resource
    private InsertMapper insertMapper;

    @Autowired
    private IndicatorService indicatorService;

    @Monitor("新建专利")
    @Override
    public GeneralResult insertPatent(Patent patent) {
        if (null == patent.getIndicatorList() || patent.getIndicatorList().isEmpty()) {
            int a = insertMapper.insertPatent(patent);
            if (a > 0) {
                return GeneralResult.build(0, "success");
            }
            return GeneralResult.build(1, "failed");
        } else {
            for (int i = 0; i < patent.getIndicatorList().size(); i++) {
                if (null == patent.getIndicatorList().get(i)) {
                    return GeneralResult.build(1, "failed");
                }
                patent.getIndicatorList().get(i).setPatentId(patent.getPatentId());
                patent.getIndicatorList().get(i).setIndicatorId(UUID.getUUID());
            }
            int a = insertMapper.insertPatent(patent);
            int b = indicatorService.save(patent.getIndicatorList());
            if (a > 0 && b > 0) {
                return GeneralResult.build(0, "success");
            }
            return GeneralResult.build(1, "failed");
        }
    }

}
