package com.sxp.patMag.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.*;

@Aspect
@Component
@EnableAspectJAutoProxy
public class WeLogFile {

    private static String path = "D:\\idea\\patent\\patentManageSystem\\src\\main\\webapp\\file\\weLog.log";

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
//    @After("doLog()")
    @Around("doLog()")
    public Object writeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = Logger.getLogger(path);
        log.setLevel(Level.ALL);
        FileHandler fileHandler = new FileHandler(path, true);//true是以追加的形式
        fileHandler.setLevel(Level.ALL);
        fileHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                String sDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss").format(new Date());
                return sDate + "|" + record.getLevel() + "|"
                        + record.getMessage() + "\n";
            }
        });
        log.addHandler(fileHandler);
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取参数
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        Object proceed = joinPoint.proceed();
        if (proceed != null) {
            log.info("bai" + "|" + methodName + "|incoming paramter:" + args.toString() + "|returning value is " + proceed);
        } else {
            log.info("bai" + "|" + methodName + "|incoming paramter:" + args.toString());
        }
        fileHandler.close();
        return proceed;
    }

    /**
     * 管理员读取日志
     *
     * @throws IOException
     */
    public static void readLog() throws IOException {
        // 读取文件
//        File file = new File(path);
//        Scanner sc = new Scanner(file);
//        sc.useDelimiter("\\Z");
//        String s = sc.next();
//        System.out.println(s);

        //读取文件
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            StringBuilder stringBuilder = new StringBuilder();
            /* read log line by line */
            while ((strLine = br.readLine()) != null) {
                stringBuilder.append(strLine + "\n");
                /* parse strLine to obtain what you want */
            }
            System.out.println(stringBuilder.toString());
            fstream.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
