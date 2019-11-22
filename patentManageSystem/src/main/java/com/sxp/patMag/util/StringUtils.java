package com.sxp.patMag.util;

import java.util.Collection;
import java.util.Map;

/**
 * Author： Jude
 * Date:2019/11/20
 * Time:10:45
 */

public class StringUtils {

    public final static boolean isNull(Object[] objs) {
        if (objs == null || objs.length == 0)
            return true;
        return false;
    }
    public final static boolean isNull(Object obj) {
        if (obj == null || isNull(obj.toString())){
            return true;
        }
        return false;
    }

    public final static boolean isNull(Integer integer) {
        if (integer == null || integer == 0)
            return true;
        return false;
    }

    public final static boolean isNull(Collection collection) {
        if (collection == null || collection.size() == 0)
            return true;
        return false;
    }

    public final static boolean isNull(Map map) {
        if (map == null || map.size() == 0)
            return true;
        return false;
    }

    public final static boolean isNull(String str) {
        return str == null || "".equals(str.trim())
                || "null".equals(str.toLowerCase());
    }

    public final static boolean isNull(Long longs) {
        if (longs == null || longs == 0)
            return true;
        return false;
    }

    public final static boolean isNotNull(Long longs) {
        return !isNull(longs);
    }

    public final static boolean isNotNull(String str) {
        return !isNull(str);
    }

    public final static boolean isNotNull(Collection collection) {
        return !isNull(collection);
    }

    public final static boolean isNotNull(Map map) {
        return !isNull(map);
    }

    public final static boolean isNotNull(Integer integer) {
        return !isNull(integer);
    }

    public final static boolean isNotNull(Object[] objs) {
        return !isNull(objs);
    }
    public final static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public final static boolean isNotEmpty(Object obj){
        return !isNull(obj);
    }
}