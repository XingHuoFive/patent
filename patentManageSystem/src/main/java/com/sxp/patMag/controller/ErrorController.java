package com.sxp.patMag.controller;

import com.sxp.patMag.util.GeneralResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Andy
 * @time: 2019/11/28 10:49
 */

@RestController

public class ErrorController {

    @RequestMapping("/e404")
    public GeneralResult onE404Error(){
        return GeneralResult.build(404,"服务器出小差了");
    }

}
