package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.InsertService;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Mrs.Wang
 * @create 2019-11-19 17:44
 */

@Controller
@RequestMapping("/insert")
public class InsertController{

    @Autowired
    private InsertService insertService;
    @RequestMapping(value="/insertPatent", method= RequestMethod.POST)
    @ResponseBody
    public GeneralResult insertPatent(Patent patent){
        return insertService.InsertPatent(patent);
    }
}
