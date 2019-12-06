package com.sxp.patMag.util;

import com.sxp.patMag.entity.LogPo;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.enums.ActionEnum;
import com.sxp.patMag.enums.NounEnum;
import com.sxp.patMag.enums.RedisEnum;
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

import java.io.*;
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
    private RedisUtil redis;

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

    private ThreadLocal<User> user = new ThreadLocal<User>();

    public void setUser(ThreadLocal<User> user) {
        this.user = user;
    }

    @Value("${logExpireTime}")
    private int logExpireTime;

    public int getLogExpireTime() {
        return logExpireTime;
    }

    public void setLogExpireTime(int logExpireTime) {
        this.logExpireTime = logExpireTime;
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
                StringBuilder stringBuilder = new StringBuilder();
                String sDate =DateUtils.formatDate(System.currentTimeMillis());
                stringBuilder.append(sDate);
                stringBuilder.append("--");
                stringBuilder.append(record.getLevel());
                stringBuilder.append("--");
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
        if (user.get() != null) {
            username = user.get().getUserName();
        }
        if (username != null && proceed != null) {
            log.info(username + "--" + methodName + "--paramter:" + args.toString() + "returning:" + proceed.toString());
        } else if (proceed == null) {
            log.info(username + "--" + methodName + "--paramter:" + args.toString());
        } else {
            try {
                throw new ServiceException(PatentException.LOGIN_ERR);
            } catch (ServiceException e) {
                e.getExceptionEnum().getMessage();
            }
        }

        /*               redis            */
        String sDate =DateUtils.formatDate(System.currentTimeMillis());
        LogPo logPo = new LogPo();
        logPo.setCreateTime(sDate);
        logPo.setUserName(username);

        if (methodName.contains(ActionEnum.SELECT1.getName()) || methodName.contains(ActionEnum.SELECT2.getName()) || methodName.contains(ActionEnum.SELECT3.getName()) || methodName.contains(ActionEnum.SELECT4.getName()) || methodName.contains(ActionEnum.SELECT5.getName())) {
            logPo.setItem(ActionEnum.SELECT.getName() + retString(methodName));
            logPo.setOperation(replaceMethod(methodName));
        }
        if (methodName.contains(ActionEnum.UPDATE1.getName())) {
            logPo.setItem(ActionEnum.UPDATE.getName()  + retString(methodName));
            logPo.setOperation(replaceMethod(methodName));
        }
        if (methodName.contains(ActionEnum.LOGIN1.getName())) {
            logPo.setItem(ActionEnum.LOGIN.getName() );
            logPo.setOperation(replaceMethod(methodName));
        }
        if (methodName.contains(ActionEnum.INSERT1.getName()) || methodName.contains(ActionEnum.INSERT2.getName()) || methodName.contains(ActionEnum.INSERT3.getName())) {
            logPo.setItem(ActionEnum.SELECT.getName() + retString(methodName));
            logPo.setOperation(replaceMethod(methodName));
        }
        if (methodName.contains(ActionEnum.CHECK1.getName())) {
            logPo.setItem(ActionEnum.CHECK.getName()  + retString(methodName));
            logPo.setOperation(replaceMethod(methodName));
        }
        if (methodName.contains(ActionEnum.EXPORT1.getName())) {
            logPo.setItem(ActionEnum.EXPORT.getName() );
            logPo.setOperation("****");
        }
        if (methodName.contains(ActionEnum.SUBMIT1.getName())) {
            logPo.setItem(ActionEnum.SUBMIT.getName() );
            logPo.setOperation("****");
        }
        if (methodName.contains(ActionEnum.UPLOAD1.getName())) {
            logPo.setItem(ActionEnum.UPLOAD.getName() );
            logPo.setOperation("****");
        }
        if (methodName.contains(ActionEnum.READ1.getName())) {
            fileHandler.close();
            return proceed;
        }
        if (redis.lGetListSize(RedisEnum.LOGKEY.getName())<=1000){
            redis.lpush(RedisEnum.LOGKEY.getName(),logPo);
        }else{
            redis.rpop(RedisEnum.LOGKEY.getName());
            redis.lpush(RedisEnum.LOGKEY.getName(),logPo);
        }
        redis.expire(RedisEnum.LOGKEY.getName(),logExpireTime);
        fileHandler.close();
        return proceed;
    }

    /**
     * 获取日志的路径
     */
    public static void getPath() {
        File file = new File("");
        String absolutePath = file.getAbsolutePath();
        path = absolutePath + "\\patentManageSystem\\src\\main\\webapp\\file\\weLog.log";
        File file1 = new File(path);
        File parent = file1.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (file1.length() > 5048576) {
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
    public static String retString(String str) {
        if (str.contains(NounEnum.HISTORY1.getName())) {
            return NounEnum.HISTORY.getName();
        }
        if (str.contains(NounEnum.INDICATOR1.getName())) {
            return NounEnum.INDICATOR.getName();
        }
        if (str.contains(NounEnum.REMARKVIEW1.getName())) {
            return NounEnum.REMARKVIEW.getName();
        }
        if (str.contains(NounEnum.NOTICE1.getName())) {
            return NounEnum.NOTICE.getName();
        }
        if (str.contains(NounEnum.PATENT1.getName())) {
            return NounEnum.PATENT.getName();
        }
        if (str.contains(NounEnum.JBOOK1.getName())) {
            return NounEnum.JBOOK.getName();
        }

        return "";
    }
    
    
    public static  String replaceMethod(String methodName){
        return methodName.replace(methodName.substring(3, methodName.length() - 1), "****");
    }

}
