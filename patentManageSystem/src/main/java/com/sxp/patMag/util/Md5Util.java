package com.sxp.patMag.util;

import java.security.MessageDigest;


import java.security.MessageDigest;


/**
 * @author： Jude
 * @date:2019/11/27
 * @time:17:51
 */
/**
 * MD5加密工具类
 */
public class Md5Util {
    //盐，用于混交md5
    private static final String slat = "&%5123***&&%%$$#@";

    public static String encrypt(String dataStr) {
        try {
            dataStr = dataStr + slat;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getMd5Keys(String dataStr) {
        return  encrypt(encrypt(dataStr));
    }


}
