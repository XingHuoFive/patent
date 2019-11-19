package com.sxp.patMag.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.service.LoginService;
import com.sxp.patMag.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
/**
 * Author： Jude
 * Date:2019/11/19
 * Time:18:18
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value="/check" , method= RequestMethod.POST)
    @ResponseBody
    public Boolean checkUser(User user){

       boolean res =loginService.CheckUser(user)>0;

       return res;
    }



}