package com.sxp.patMag.controller;

import com.sxp.patMag.annotation.Monitor;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.InsertService;
import com.sxp.patMag.util.GeneralResult;
import com.sxp.patMag.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Mrs.Wang
 * @create 2019-11-19 17:44
 */

@Controller
@RequestMapping("/insert")
public class InsertController {

    @Autowired
    private InsertService insertService;

    @RequestMapping(value = "/insertPatent", method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult insertPatent(@RequestBody @Valid Patent patent) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        patent.setPatentId(UUID.getUUID());
        patent.setApplyTime(date);
        patent.setPatentSchedule("未审核");
        patent.setPatentClaim("0");
        return insertService.insertPatent(patent);
    }


}
