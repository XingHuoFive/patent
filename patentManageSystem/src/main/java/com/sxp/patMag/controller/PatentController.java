package com.sxp.patMag.controller;

import com.sxp.patMag.service.PatentService;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/patent")
public class PatentController {

    @Autowired
    private PatentService tbPatentService;

    @RequestMapping(value = "/selectTbPatentById/{patentId}", method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult selectTbPatentById(@PathVariable("patentId") String patentId) {
        return tbPatentService.selectById(patentId);
    }


}