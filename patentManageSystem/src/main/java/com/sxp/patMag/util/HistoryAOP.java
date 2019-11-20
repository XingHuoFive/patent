package com.sxp.patMag.util;
import com.alibaba.fastjson.JSON;
import com.sxp.patMag.annotation.Monitor;
import com.sxp.patMag.entity.History;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class HistoryAOP {
    @Autowired
    private RedisUtil redis ;
    /**
     * 声明切点
     */
    @Pointcut("@annotation(com.sxp.patMag.annotation.Monitor)")
    public void getAction() {
    }

    /**
     * 使用AOP写日志文件
     *
     * @param joinPoint
     * @throws IOException
     */

    @Around("getAction()")
    public Object writeHistory(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("开始操作历史记录");


        History history = new History();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        Monitor monitor = method.getAnnotation(Monitor.class);
        if (monitor != null) {
            String value = monitor.value();
            history.setHtOperation(value);//保存获取的操作
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = JSON.toJSONString(args);

        history.setHtDate(new Date().toString());

        System.out.println(params);
        //获取用户名


        Object proceed = joinPoint.proceed();
        return proceed;
    }

}
