package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.PatentSelcetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName PatentSelcetController
 * @Description: TODO
 * @Author 硕
 * @Date 2019/11/19
 **/

@Controller
@RequestMapping("/patent")
public class PatentSelcetController {



    @Autowired
    PatentSelcetService patentSelcetService;


    /**
     * 专利查询
     *
     * @param patent
     * @return
     */
    @RequestMapping(value = "/selectPatentByPatent",method = RequestMethod.POST)
    @ResponseBody
    public List<Patent> selectPatentByPatent(@RequestBody Patent patent){
        return  patentSelcetService.selectPatentByPatent(patent);
    }



    /**
     * 查询未被认领的专利
     * @param patentClaim
     * @return
     */
    @RequestMapping(value = "/selectPatentToUser",method = RequestMethod.POST)
    @ResponseBody
    public List<Patent> selectPatentToUser(){
        System.out.println(patentSelcetService.selectPatentToUser());
        return  patentSelcetService.selectPatentToUser();
    }

}
