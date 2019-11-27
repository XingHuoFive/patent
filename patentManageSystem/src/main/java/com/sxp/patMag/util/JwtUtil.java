package com.sxp.patMag.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sxp.patMag.entity.User;

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
        Date start = new Date();
        //30min有效时间
        long currentTime = System.currentTimeMillis() + 30* 60 * 1000;
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(user.getUserId()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getUserPassword()));
        return token;
    }
}
