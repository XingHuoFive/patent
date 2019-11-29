package com.sxp.patMag.util;

import com.sxp.patMag.dao.UploadMapper;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.exception.PatentException;
import com.sxp.patMag.exception.ServiceException;
import com.sxp.patMag.service.LoginService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;

/**
 * @Author: Guofengzhang
 * Date:2019/11/20
 * Time:9:32
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class WeLogFile {

    @Autowired
    private LoginService loginService;

    /**
     * 日志存放地址
     */
    private static String path = null;

    /**
     * 当时操作人
     */
    private String username;

    private StringBuilder stringBuilder = new StringBuilder();

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");

    private User user1;

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    /**
     * 声明切点
     */
    @Pointcut("execution(* com.sxp.patMag.service.serviceImpl.*.*(..))")
    public void doLog() {
    }

    /**
     * 使用AOP写日志文件
     *
     * @param joinPoint
     * @throws IOException
     */
    @Around("doLog()")
    public Object writeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        getPath();
        Logger log = Logger.getLogger(path);
        log.setLevel(Level.ALL);
        //true是以追加的形式
        FileHandler fileHandler = new FileHandler(path, true);
        fileHandler.setLevel(Level.ALL);
        fileHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                String sDate = simpleDateFormat.format(System.currentTimeMillis());
                if (stringBuilder != null) {
                    stringBuilder.delete(0, stringBuilder.length());
                }
                stringBuilder.append(sDate);
                stringBuilder.append("|");
                stringBuilder.append(record.getLevel());
                stringBuilder.append("|");
                stringBuilder.append(record.getMessage());
                stringBuilder.append("\n");
                return stringBuilder.toString();
            }
        });

        log.addHandler(fileHandler);
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取参数
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        Object proceed = joinPoint.proceed();
        if (user1 != null) {
            username = user1.getUserName();
        }
        if (username != null && proceed != null) {
            log.info(username + "|" + methodName + "|paramter:" + args.toString() + "returning:" + proceed.toString());
        } else if (proceed == null) {
            log.info(username + "|" + methodName + "|paramter:" + args.toString());
        } else {
            try {
                throw new ServiceException(PatentException.LOGIN_ERR);
            } catch (ServiceException e) {
                e.getExceptionEnum().getMessage();
            }
        }
        fileHandler.close();
        return proceed;
    }

    /**
     * 获取日志绝对路径
     */
    public static void getPath() {
        File file = new File("");
        String absolutePath = file.getAbsolutePath();
        path = absolutePath + "\\patentManageSystem\\src\\main\\webapp\\file\\weLog.log";
        File file1 = new File(path);
        if (file1.length() > 500000000) {
            file1.delete();
        }
        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        file1 = null;
        file = null;
    }

    /**
     * 管理员读取日志
     *
     * @throws IOException
     */
    public static String readLog() {
        return path;
    }

}
