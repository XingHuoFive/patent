package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.PatentSelcetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName PatentSelcetController
 * @Description: TODO
 * @Author 硕
 * @Date 2019/11/19
 **/

@Controller
@RequestMapping("/Patent")
public class PatentSelcetController {




    @Autowired
    PatentSelcetService patentSelcetService;

    @RequestMapping("/selectPatentByPatent")
    @ResponseBody
    public List<Patent> selectPatentByPatent(Patent patent){

        System.out.println(patentSelcetService.selectPatentByPatent(patent).toString());
        return  patentSelcetService.selectPatentByPatent(patent);


    }
}
