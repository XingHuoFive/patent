package com.sxp.patMag.util;

import com.sxp.patMag.entity.User;
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
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("data");
        if (redisUtil != null) {
            User user = (User) redisUtil.get(token);
            weLogFile.setUser1(user);
        }
        return true;
    }
}
