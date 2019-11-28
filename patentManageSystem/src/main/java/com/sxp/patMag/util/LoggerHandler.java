package com.sxp.patMag.util;

import com.sxp.patMag.dao.LoginMapper;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author： Jude
 * Date:2019/11/27
 * Time:12:54
 */
@Component
public class LoggerHandler extends HandlerInterceptorAdapter {
    @Autowired
    WeLogFile weLogFile;
    @Autowired
    LoginMapper mapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("data");
        weLogFile.setUser1(mapper.selectUserById(token).get(0));
        return true;
    }
}
