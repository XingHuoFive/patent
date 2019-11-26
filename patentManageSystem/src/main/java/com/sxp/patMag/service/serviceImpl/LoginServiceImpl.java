package com.sxp.patMag.service.serviceImpl;


import com.sxp.patMag.dao.LoginMapper;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.service.LoginService;
import com.sxp.patMag.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * @author： Jude
 * @date:2019/11/26
 * @time:11:21
 */

@Service
public class LoginServiceImpl implements LoginService  {

    @Resource
    private LoginMapper loginMapper;
    @Autowired
    private RedisUtil redis ;

    @Autowired
    private WeLogFile weLogFile;
    @Autowired
    private HistoryAOP historyAOP;
    @Override
    public GeneralResult login(User user) {

        System.out.println(user.toString());
//        user.setUserName(user.getUserName().trim());
//        user.setUserPassword(user.getUserPassword().trim());




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
        if ( user.getUserPassword().length()>16 ||user.getUserName().length()>16 ){
            return GeneralResult.build(1, "用户名或密码长度超过了最大长度");
        }

        List<User> list = loginMapper.CheckUser(user);
        if (list == null || list.size() == 0) {
            //返回登录失败
            return GeneralResult.build(1, "用户名或密码不正确");
        }

        //生成token，使用uuid
        String token = UUID.getUUID();
        //清空密码
        user.setUserPassword(null);
        historyAOP.setUser(user);
        weLogFile.setUser1(user);
         //权限存储
         user.setUserRole(list.get(0).getUserRole());
         user.setUserId(list.get(0).getUserId());
        //把用户信息保存到redis，key就是token，value就是用户信息
        redis.set("UserLogin" + ":" + token, JsonUtils.objectToJson(user));
        //设置key的过期时间
        redis.expire("UserLogin" + ":" + token, 1800);
        //返回登录成功，其中要把token返回。
        return GeneralResult.build(0, user.getUserRole(),token);
    }
    @Override
    public GeneralResult getUserByToken(String token) {
        String json = (String) redis.get("UserLogin" + ":" + token);
        if ( json==null||json.length()==0) {
            return GeneralResult.build(1, "用户登录已经过期");
        }
        //重置Session的过期时间
        redis.expire("UserLogin" + ":" + token, 1800);
        //把json转换成User对象
        User user = JsonUtils.jsonToPojo(json, User.class);
        return GeneralResult.ok(user);
    }



}
