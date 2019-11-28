package com.sxp.patMag.interceptor;

import com.sxp.patMag.dao.LoginMapper;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.service.LoginService;
import com.sxp.patMag.util.JwtUtil;
import com.sxp.patMag.util.WeLogFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
        String userId = JwtUtil.getTokenUserId(token);
        List<User> list = mapper.selectUserById(userId);
        User user = list.get(0);
        weLogFile.setUser1(user);
        return true;
    }
}
