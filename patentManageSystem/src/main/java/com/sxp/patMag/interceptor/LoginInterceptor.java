package com.sxp.patMag.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sxp.patMag.annotation.PassToken;
import com.sxp.patMag.dao.LoginMapper;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.enums.ProcessEnum;
import com.sxp.patMag.util.HistoryReflect;
import com.sxp.patMag.util.Md5Util;
import com.sxp.patMag.util.RedisUtil;
import com.sxp.patMag.util.WeLogFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;


/**
 * Author： Jude
 * Date:2019/11/25
 * Time:15:15
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private WeLogFile weLogFile;
    @Autowired
    private HistoryReflect reflect;
    @Autowired
    private RedisUtil redis;
    @Autowired
    private LoginMapper loginMapper;

    private ThreadLocal<User> user = new ThreadLocal<User>();

    public void setUser(ThreadLocal<User> user) {
        this.user = user;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        // 从 http 请求头中取出 token
        String token = request.getHeader("data");
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        if(null == token){
            responseWeb("401", "tokenIsNull", response);
            return false;
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            log.info("token解析错误");
            responseWeb("401", "tokenDecodeError", response);
            return false;
        }
        // 获取 token 中的 user id
        Object userJson = redis.get(ProcessEnum.USERLOGIN.getName() + Md5Util.getMd5Keys(userId));
        if (userJson == null) {
            log.info("token过期");
            responseWeb("401", "tokenOutOfDate", response);
            return false;
        }
        List<User> list = loginMapper.selectUserById(userId);
        if (list == null || list.size() == 0) {
            log.info("用户不存在");
            responseWeb("401", "NoSuchUser", response);
            return false;
        }
        user.set(list.get(0));
        reflect.setUser(user);
        weLogFile.setUser(user);
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(list.get(0).getUserPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            log.info("用户密码校验错误");
            responseWeb("401", "tokenVerifyError", response);
            return false;
        }
        return true;
    }

    public static void responseWeb(String status, String msg, HttpServletResponse response) throws IOException {
        JSONObject res = new JSONObject();
        PrintWriter out = null;
        res.put("status", status);
        res.put("msg", msg);
        out = response.getWriter();
        out.append(res.toString());
        out.close();
    }
/*
        JSONObject res = new JSONObject();
        // 获取 token 中的 user id
        Object userJson = redis.get("UserLogin" + ":" + token);
        if (userJson == null) {

            res.put("status", 1);
            res.put("msg", "token过期");
            out = response.getWriter();
            out.append(res.toString());

            log.info("token过期");
            return false;
        }
        User user = JSON.parseObject(userJson.toString(), User.class);
        if (user == null) {
            res.put("status", 1);
            res.put("msg", "用户不存在");
            out = response.getWriter();
            out.append(res.toString());
            log.info("用户不存在");
            return false;
        }*/


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}