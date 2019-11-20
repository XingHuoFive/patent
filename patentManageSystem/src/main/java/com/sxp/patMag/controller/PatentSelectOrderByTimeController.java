package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.PatentSelectOrderByTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Mrs.Wang
 * @create 2019-11-20 15:05
 */
@Controller
@RequestMapping("/patent")
public class PatentSelectOrderByTimeController {

    @Autowired
    PatentSelectOrderByTimeService patentSelectOrderByTimeService;

    /**
     * 专利查询
     *
     * @param patent
     * @return
     */
    @RequestMapping(value = "/selectPatentByPatentOrderbyTime",method = RequestMethod.POST)
    @ResponseBody
    public List<Patent> selectPatentByPatentOrderbyTime(@RequestBody Patent patent){
        return  patentSelectOrderByTimeService.selectPatentByPatentOrderbyTime(patent);
    }

    /**
     * 查询未被认领的专利
     * @param
     * @return
     */
    @RequestMapping(value = "/selectPatentToAdmin",method = RequestMethod.POST)
    @ResponseBody
    public List<Patent> selectPatentToAdmin(){
        System.out.println(patentSelectOrderByTimeService.selectPatentToAdmin());
        return  patentSelectOrderByTimeService.selectPatentToAdmin();
    }
}
