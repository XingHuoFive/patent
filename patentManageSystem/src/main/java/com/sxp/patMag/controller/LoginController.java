package com.sxp.patMag.controller;

import com.sxp.patMag.annotation.Monitor;
import com.sxp.patMag.annotation.PassToken;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.service.LoginService;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @PassToken
    public GeneralResult login(@RequestBody User user){
        return loginService.login(user);
    }

    @RequestMapping(value="/loginByToken/{data}" , method= RequestMethod.POST)
    @ResponseBody
    @PassToken
    public GeneralResult loginByToken(@PathVariable("data") String data){
        return loginService.getUserByToken(data);
    }

}