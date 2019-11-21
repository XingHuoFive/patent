package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.PatentService;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/patent")
public class PatentController {

    @Autowired
    private PatentService tbPatentService;

    @RequestMapping(value = "/selectTbPatentById/{patentId}", method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult selectTbPatentById(@PathVariable("patentId") String patentId) {
        return tbPatentService.selectById(patentId);
    }

    @PostMapping("/update")
    public GeneralResult update(@RequestBody Patent patent) {
        GeneralResult generalResult = new GeneralResult();
        if (null == patent) {
            generalResult.setStatus(1);
            generalResult.setMsg("patent为空，无法更改");
            return generalResult;
        }
        if (null == patent.getPatentId() || "".equals(patent.getPatentId())) {
            generalResult.setStatus(1);
            generalResult.setMsg("id为空，无法更改");
            return generalResult;
        }
        int i = tbPatentService.updatePatent(patent);
        if (i <= 0) {
            generalResult.setStatus(1);
            generalResult.setMsg("更新失败");
            return generalResult;
        }
        generalResult.setStatus(0);
        generalResult.setMsg("更新成功");
        return generalResult;
    }

}