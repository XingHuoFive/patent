package com.sxp.patMag.controller;

import com.sxp.patMag.entity.User;
import com.sxp.patMag.service.LoginService;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

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

    @RequestMapping(value="/login" , method= RequestMethod.POST)
    @ResponseBody
    public GeneralResult login(User user){
        return loginService.login(user);
    }

    @RequestMapping(value="/loginByToken" , method= RequestMethod.POST)
    @ResponseBody
    public GeneralResult loginByToken(String token){
        return loginService.getUserByToken(token);
    }

}