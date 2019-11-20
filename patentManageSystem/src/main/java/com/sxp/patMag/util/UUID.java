package com.sxp.patMag.util;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.util
 * @ClassName: UUID
 * @date 2019/11/19 19:07
 */
public class UUID {
    public static String getUUID() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }
}
