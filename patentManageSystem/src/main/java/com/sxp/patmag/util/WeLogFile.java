package com.sxp.patMag.util;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sxp.patMag.dao.UploadMapper;
import com.sxp.patMag.entity.LogPo;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.enums.ProcessEnum;
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


    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");

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
                String sDate = simpleDateFormat.format(System.currentTimeMillis());
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
        String sDate = simpleDateFormat.format(System.currentTimeMillis());
        LogPo logPo = new LogPo();
        logPo.setCreateTime(sDate);
        logPo.setUserName(username);

        if (methodName.contains("select") || methodName.contains("get") || methodName.contains("list") || methodName.contains("List") || methodName.contains("show")) {
            logPo.setItem("查询" + retString(methodName));
            logPo.setOperation(methodName.replace(methodName.substring(3, methodName.length() - 1), "****"));
        }
        if (methodName.contains("update")) {
            logPo.setItem("修改" + retString(methodName));
            logPo.setOperation(methodName.replace(methodName.substring(3, methodName.length() - 1), "****"));
        }
        if (methodName.contains("login")) {
            logPo.setItem("用户登录");
            logPo.setOperation(methodName.replace(methodName.substring(3, methodName.length() - 1), "****"));
        }
        if (methodName.contains("add") || methodName.contains("insert") || methodName.contains("list")) {
            logPo.setItem("新增" + retString(methodName));
            logPo.setOperation(methodName.replace(methodName.substring(3, methodName.length() - 1), "****"));
        }
        if (methodName.contains("check")) {
            logPo.setItem("审核" + retString(methodName));
            logPo.setOperation(methodName.replace(methodName.substring(3, methodName.length() - 1), "****"));
        }
        if (methodName.contains("export")) {
            logPo.setItem("导出");
            logPo.setOperation("****");
        }
        if (methodName.contains("submit")) {
            logPo.setItem("提交");
            logPo.setOperation("****");
        }
        if (methodName.contains("upload")) {
            logPo.setItem("上传文件");
            logPo.setOperation("****");
        }
        if (methodName.contains("read")) {
            fileHandler.close();
            return proceed;
        }

        if (redis.lGetListSize("logs")<=1000){
            redis.lpush("logs",logPo);
        }else{
            redis.rpop("logs");
            redis.lpush("logs",logPo);
        }
        redis.expire("logs",logExpireTime);
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
        if (str.contains("History")) {
            return "历史记录";
        }
        if (str.contains("Indicator")) {
            return "指标";
        }
        if (str.contains("RemarkView")) {
            return "专利的备注状态";
        }
        if (str.contains("Notice")) {
            return "通知";
        }
        if (str.contains("Patent")) {
            return "专利";
        }
        if (str.contains("Jbook")) {
            return "交底书";
        }

        return "";
    }

}
