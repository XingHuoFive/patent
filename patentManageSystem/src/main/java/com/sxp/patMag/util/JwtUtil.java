package com.sxp.patMag.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sxp.patMag.entity.User;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author： Jude
 * @date:2019/11/27
 * @time:15:31
 */

public class JwtUtil {

    @Value("${expireTime}")
    private int expireTime;

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public static String getTokenUserId(String token) {
        String userId = JWT.decode(token).getAudience().get(0);
        return userId;
    }

    public static String getToken(User user) {
       JwtUtil jwtUtil = new JwtUtil();
        String token = "";
        //日期转字符串
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,jwtUtil.getExpireTime());
        //特定时间的年后.withExpiresAt(date)
        Date date = calendar.getTime();
        token = JWT.create().withAudience(user.getUserId())
                .sign(Algorithm.HMAC256(user.getUserPassword()));
        return token;
    }



}
