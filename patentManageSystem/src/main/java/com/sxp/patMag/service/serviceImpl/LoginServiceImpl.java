package com.sxp.patMag.service.serviceImpl;


import com.sxp.patMag.dao.LoginMapper;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.enums.ProcessEnum;
import com.sxp.patMag.service.LoginService;
import com.sxp.patMag.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author： Jude
 * @date:2019/11/26
 * @time:11:21
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginMapper loginMapper;
    @Autowired
    private RedisUtil redis;

    @Autowired
    private WeLogFile weLogFile;
    @Autowired
    private HistoryReflect reflect;
    @Value("${expireTime}")
    private Integer expireTime;


    @Override
    public GeneralResult login(User user) {
        List<User> list = loginMapper.checkUser(user);
        if (list == null || list.size() == 0) {
            //返回登录失败
            return GeneralResult.build(1, "用户名或密码不正确");
        }
        //生成token，使用uuid
        String token =null;
        String userMd5 = Md5Util.encrypt(list.get(0).getUserId());
        //清空密码
        user.setUserPassword(null);
        reflect.setUser(user);
        weLogFile.setUser1(user);
        //权限存储
        user.setUserRole(list.get(0).getUserRole());
        user.setUserId(list.get(0).getUserId());
        if (redis.get(ProcessEnum.USERLOGIN.name() +userMd5)==null){
        token= JwtUtil.getToken(list.get(0));
        //把用户信息保存到redis，key就是token，value就是用户信息
        redis.set(ProcessEnum.USERLOGIN.getName() + userMd5, token);
        }else {
        token = redis.get(ProcessEnum.USERLOGIN .getName()+userMd5).toString();
        }
        //重置key的过期时间
        redis.expire(ProcessEnum.USERLOGIN.getName() + userMd5, expireTime);
        //返回登录成功，其中要把token返回。
        return GeneralResult.build(0, user.getUserRole(), token);
    }

    @Override
    public GeneralResult getUserByToken(HttpServletRequest request) {
        String token = request.getHeader("data");
        String userId =JwtUtil.getTokenUserId(token);
        return GeneralResult.build(0,"success",loginMapper.selectUserById(userId));

    }

    @Override
    public GeneralResult invalidate(HttpServletRequest request) {
        String token = request.getHeader("data");
        String userId =JwtUtil.getTokenUserId(token);
        try {
            redis.del(Md5Util.encrypt(userId));
        }catch (Exception e){
            e.printStackTrace();
        }
         return GeneralResult.ok();
    }


}
