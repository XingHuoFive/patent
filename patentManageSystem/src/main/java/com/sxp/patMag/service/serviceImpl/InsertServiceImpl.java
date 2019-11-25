package com.sxp.patMag.service.serviceImpl;

import com.sxp.patMag.annotation.Monitor;
import com.sxp.patMag.dao.InsertMapper;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.InsertService;
import com.sxp.patMag.util.GeneralResult;
import com.sxp.patMag.util.UUID;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


/**
 * @author Mrs.Wang
 * @create 2019-11-19 18:59
 */
@Service
public class InsertServiceImpl implements InsertService {

    @Resource
    private InsertMapper insertMapper;

    @Monitor("新建专利")
    public GeneralResult InsertPatent(Patent patent){

        if (null == patent.getCreatePerson() || "".equals(patent.getCreatePerson())) {
            return  GeneralResult.build(1,"发明人不能为空");
        }
        if (null == patent.getPatentName() || "".equals(patent.getPatentName())) {
            return  GeneralResult.build(1,"专利名称不能为空");
        }
        if (null == patent.getApplyPerson() || "".equals(patent.getApplyPerson())) {
            return  GeneralResult.build(1,"申请人不能为空");
        }



        int a = insertMapper.InsertPatent(patent);
        if(a>0){
            return GeneralResult.build(0,"success");
        }
        return GeneralResult.build(1,"failed");
    }

}
