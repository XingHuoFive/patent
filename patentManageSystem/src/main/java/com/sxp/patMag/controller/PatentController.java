package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/patent")
public class PatentController {

    @Autowired
    private PatentService tbPatentService;

    @RequestMapping(value="/selectTbPatentById/{patentId}" , method= RequestMethod.POST)
    @ResponseBody
    public Patent selectTbPatentById(@PathVariable("patentId") String patentId){

        Map<String,Object> map  =  new  HashMap<String,Object>();
        Patent tbPatent =  tbPatentService.selectById(patentId);
//        if (tbPatent!=null){
//            map.put("code",0);
//            map.put("data",tbPatent);
//        }else{
//            map.put("code",1);
//            map.put("data",null);
//        }
//        System.out.println(map);
           return tbPatent;
    }



}