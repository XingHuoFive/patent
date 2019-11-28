package com.sxp.patMag.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sxp.patMag.entity.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author： Jude
 * @date:2019/11/27
 * @time:15:31
 */
public class JwtUtil {

    public static String getTokenUserId(String token) {
        String userId = JWT.decode(token).getAudience().get(0);
        return userId;
    }

    public static String getToken(User user) {

        String token = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //日期转字符串
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,30 );
        //特定时间的年后
        Date date = calendar.getTime();


        token = JWT.create().withAudience(user.getUserId()).withExpiresAt(date)
                .sign(Algorithm.HMAC256(user.getUserPassword()));
        return token;
    }
}
