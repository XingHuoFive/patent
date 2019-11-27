package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.PatentService;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.controller
 * @ClassName: PatentController
 * @date 2019/11/26 17:08
 */
@RestController
@RequestMapping("/patent")
public class PatentController {

    @Autowired
    private PatentService tbPatentService;

    @PostMapping("/selectPatentById/{patentId}")
    @ResponseBody
    public GeneralResult selectTbPatentById(@PathVariable("patentId") String patentId) {
        GeneralResult generalResult = new GeneralResult();
        if (null == patentId || "".equals(patentId)) {
            generalResult.setStatus(1);
            generalResult.setMsg("id为空，无法查询");
            return generalResult;
        }
        Patent patent = tbPatentService.selectById(patentId);
        if (null == patent) {
            generalResult.setStatus(1);
            generalResult.setMsg("为找到对应专利");
            return generalResult;
        }
        generalResult.setStatus(0);
        generalResult.setMsg("查询成功");
        generalResult.setData(patent);
        return generalResult;
    }

    @PostMapping("/update")
    public GeneralResult update(@RequestBody Patent patent) {
        GeneralResult generalResult = new GeneralResult();
        if (null == patent) {
            generalResult.setStatus(1);
            generalResult.setMsg("patent为空，无法更改");
            return generalResult;
        }
        if (null == patent.getPatentId() || "".equals(patent.getPatentId()) || patent.getPatentId().length() != 32) {
            generalResult.setStatus(1);
            generalResult.setMsg("id有误，无法更改");
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