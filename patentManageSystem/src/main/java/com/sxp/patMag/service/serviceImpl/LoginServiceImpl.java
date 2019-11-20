package com.sxp.patMag.service.serviceImpl;


import com.sxp.patMag.dao.LoginMapper;
import com.sxp.patMag.dao.PatentMapper;

import com.sxp.patMag.entity.User;
import com.sxp.patMag.service.LoginService;
import com.sxp.patMag.service.PatentService;
import com.sxp.patMag.util.GeneralResult;
import com.sxp.patMag.util.JsonUtils;
import com.sxp.patMag.util.RedisUtil;
import com.sxp.patMag.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService  {

    @Resource
    private LoginMapper loginMapper;
    @Autowired
    private RedisUtil redis ;
    public GeneralResult login(User user) {

        user.setUserName(user.getUserName().trim());
        user.setUserPassword(user.getUserPassword().trim());

        //判断用户名和密码是否正确
        if (user==null){
            return GeneralResult.build(1, "用户名或密码为空");
        }
        if (StringUtils.isNull(user.getUserName())) {

            return GeneralResult.build(1, "用户名或密码为空");
        }
        if (StringUtils.isNull(user.getUserPassword())){

            return GeneralResult.build(1, "用户名或密码为空");
        }


        List<User> list = loginMapper.CheckUser(user);
        if (list == null || list.size() == 0) {
            //返回登录失败
            return GeneralResult.build(1, "用户名或密码不正确");
        }

        //生成token，使用uuid
        String token = UUID.randomUUID().toString();
        //清空密码
        user.setUserPassword(null);
//        //权限存储
//        user.setUserRole(list.get(0).getUserRole());
        //把用户信息保存到redis，key就是token，value就是用户信息
        redis.set("UserLogin" + ":" + token, JsonUtils.objectToJson(user));
        //设置key的过期时间
        redis.expire("UserLogin" + ":" + token, 86400);
        //返回登录成功，其中要把token返回。
        return GeneralResult.ok(token);
    }

    public GeneralResult getUserByToken(String token) {
        String json = (String) redis.get("UserLogin" + ":" + token);
        if ( json==null||json.length()==0) {
            return GeneralResult.build(1, "用户登录已经过期");
        }
        //重置Session的过期时间
        redis.expire("UserLogin" + ":" + token, 86400);
        //把json转换成User对象
        User user = JsonUtils.jsonToPojo(json, User.class);
        return GeneralResult.ok(user);
    }







}
