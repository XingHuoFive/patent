package com.sxp.patMag.interceptor;

import com.alibaba.fastjson.JSON;
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
import com.sxp.patMag.exception.PatentException;
import com.sxp.patMag.exception.ServiceException;
import com.sxp.patMag.service.LoginService;
import com.sxp.patMag.util.Md5Util;
import com.sxp.patMag.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private RedisUtil redis;
    @Autowired
    private LoginMapper loginMapper;

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
        JSONObject res = new JSONObject();
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(PatentException.TOKEN_PARSE_ERR);
        }
        // 获取 token 中的 user id
        Object userJson = redis.get(ProcessEnum.USERLOGIN.getName() +Md5Util.getMd5Keys(userId));
        if (userJson == null) {
            log.info("token过期");
            throw new ServiceException(PatentException.TOKEN_ERR);

        }

        List<User> list = loginMapper.selectUserById(userId);
        if (list==null || list.size()==0) {
            throw new ServiceException(PatentException.TOKEN_ERR);
        }
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(list.get(0).getUserPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(PatentException.TOKEN_PARSE_ERR);
        }
        return true;
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