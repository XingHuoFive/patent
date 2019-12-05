package com.sxp.patMag.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author： Jude
 * @date:2019/12/5
 * @time:15:31
 */
public class DateUtils {

    /**
     * 日期格式化工具
     * @param date
     * @return
     */
   public static String formatDate(Date date){
       //时间格式化
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       return simpleDateFormat.format(date);

   }

}
