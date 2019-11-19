package com.sxp.patMag.controller;

import com.sxp.patMag.entity.TbPatent;
import com.sxp.patMag.service.TbPatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/patent")
public class TbPatentController {

    @Autowired
    private TbPatentService tbPatentService;

    @RequestMapping("/selectTbPatentById/{patentId}")
    @ResponseBody
    public TbPatent selectTbPatentById(@PathVariable("patentId") String patentId){
        System.out.println(tbPatentService.selectById(patentId).toString());
        return tbPatentService.selectById(patentId);
    }



}