package com.sxp.patMag.controller;

import com.sxp.patMag.annotation.Monitor;
import com.sxp.patMag.annotation.PassToken;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.service.LoginService;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
    public GeneralResult login(@RequestBody @Valid User user , BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return GeneralResult.build(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return loginService.login(user);


    }

    /**
     *
     * @param request
     * @return 根据token查询用户信息
     */
    @RequestMapping(value="/loginByToken" , method= RequestMethod.POST)
    @ResponseBody
    @PassToken
    public GeneralResult loginByToken(HttpServletRequest request){
        return loginService.getUserByToken(request);
    }

    /**
     *
     * @param request
     * @return 退出登录
     */
    @RequestMapping(value="/invalidate" , method= RequestMethod.POST)
    @ResponseBody
    @PassToken
    public GeneralResult invalidate(HttpServletRequest request){return  loginService.invalidate(request);     }

}