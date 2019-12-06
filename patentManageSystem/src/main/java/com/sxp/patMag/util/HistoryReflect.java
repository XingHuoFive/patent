package com.sxp.patMag.util;

import com.alibaba.fastjson.JSON;
import com.sxp.patMag.annotation.Monitor;
import com.sxp.patMag.entity.History;
import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.enums.ActionEnum;
import com.sxp.patMag.enums.ProcessEnum;
import com.sxp.patMag.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author： Jude
 * @date:2019/11/26
 * @time:11:21
 */
@Slf4j
@Aspect
@Component
public class HistoryReflect {


    @Autowired
    private RedisUtil redis;

    @Autowired
    private HistoryService historyService;


    private static String statusCode_One = "1";

    private static String statusCode_Zero = "0";


    private ThreadLocal<User> user = new ThreadLocal<User>();

    public void setUser(ThreadLocal<User> user) {
        this.user = user;
    }

    /**
     * 声明切点
     */

    @Pointcut("@annotation(com.sxp.patMag.annotation.Monitor)")
    public void getAction() {
    }

    /**
     * 使用AOP写日志文件
     *
     * @param array
     * @throws IOException
     */
     public static <T> Stream<T> streamOf(T[] array) {
        return ArrayUtils.isEmpty(array) ? Stream.empty() : Arrays.asList(array).stream();
    }
    @Around("getAction()")
    public Object writeHistory(ProceedingJoinPoint joinPoint) throws Throwable {
       log.info("history...");
       try {
           History history = new History();
           //从切面织入点处通过反射机制获取织入点处的方法
           MethodSignature signature = (MethodSignature) joinPoint.getSignature();
           //获取切入点所在的方法
           Method method = signature.getMethod();
           //获取操作
           Monitor monitor = method.getAnnotation(Monitor.class);
           String value = null;
           if (monitor != null) {
               value = monitor.value();
               history.setHtOperation(value);
               //保存获取的操作
           }
           log.info(value);
           //请求的参数
           Object[] args = joinPoint.getArgs();
           String params = null;
           //时间格式化
           String date = DateUtils.formatDate(new Date());
           history.setHtDate(date);
           history.setHtId(UUID.getUUID());
           history.setHtUserId(user.get().getUserId());
           if (value.contains(ProcessEnum.UPLOAD.getName())) {
               if (ProcessEnum.UPLOADFILES.getName().equals(value)) {

                   //序列化时过滤掉request和response
                   List<Object> logArgs = streamOf(args)
                           .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                           .collect(Collectors.toList());
                   //将参数所在的数组转换成json
                   params = String.valueOf(logArgs.get(0));
                   HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                   String patentId = request.getParameter("patentId");
                   String fileType = request.getParameter("fileType");
                   history.setHtPatentId(patentId);
                   history.setHtNewItem(params);
                   history.setHtOldItem("");
                   history.setHtProcess(value);
                   history.setHtOperation(ActionEnum.UP.getName()+fileType);

               }
               if (ProcessEnum.UPLOADJBOOK.getName().equals(value)) {
                   params = JSON.toJSONString(args);
                   Jbook jbook = JSON.parseObject(params.substring(1, params.length() - 1), Jbook.class);
                   String[] jArr = jbook.getJbookUrl().split("/");
                   history.setHtPatentId(jbook.getJbookPatentId());
                   history.setHtNewItem(jArr[jArr.length - 1]);
                   history.setHtOldItem("");
                   history.setHtProcess(value);
                   history.setHtOperation(ProcessEnum.UPLOADJBOOK.getName());
               }

               historyService.insertHistory(history);
               //获取用户名
               return joinPoint.proceed();
           }
           params = JSON.toJSONString(args);
           //解析参数中的patent 成对象
           Patent patent = JSON.parseObject(params.substring(1, params.length() - 1), Patent.class);
           //从redis获取之前的专利信息
           String oldObj = redis.get("Patent" + ":" + patent.getPatentId()) != null ? redis.get("Patent" + ":" + patent.getPatentId()).toString() : null;
           //解析之前的专利为对象
           Patent oldPatent = JSON.parseObject(oldObj, Patent.class);
           ;
           //将新的对象放到redis
           redis.set("Patent" + ":" + patent.getPatentId(), JSON.toJSON(patent));
           redis.expire("Patent" + ":" + patent.getPatentId(), 86400);
           if (ProcessEnum.NEW.getName().equals(value)) {
               history.setHtPatentId(patent.getPatentId());
               history.setHtNewItem(getHistory(patent));
               history.setHtOldItem("无");
               history.setHtProcess(value);
               history.setHtOperation(ProcessEnum.NEW.getName());
           }
           if (ProcessEnum.CLAIM.getName().equals(value)) {
               history.setHtPatentId(patent.getPatentId());
               history.setHtNewItem("认领人 : " + patent.getWritePerson());
               history.setHtOldItem("认领人 : 无");
               history.setHtProcess(value);
               history.setHtOperation(ProcessEnum.CLAIM.getName());
           }
           if (ProcessEnum.CHECK.getName().equals(value)) {
               if (patent.getPatentClaim().equals(statusCode_Zero)) {
                   log.info("初审");
                   history.setHtPatentId(patent.getPatentId());
                   history.setHtNewItem("专利进度 : " + patent.getPatentSchedule());
                   history.setHtOldItem("专利进度 : 未审核");
                   history.setHtProcess(ProcessEnum.FIRSTCHECK.getName());
                   history.setHtOperation(value);
               }
               if (patent.getPatentClaim().equals(statusCode_One)) {
                   log.info("复审");
                   history.setHtPatentId(patent.getPatentId());
                   history.setHtNewItem("专利进度 : " + patent.getPatentSchedule());
                   history.setHtOldItem("专利进度 : 编写中");
                   history.setHtProcess(ProcessEnum.SECONDCHECK.getName());
                   history.setHtOperation(value);
               }
               if (patent.getSpare().equals(statusCode_Zero)) {
                   if (patent.getPatentClaim().equals(statusCode_Zero)) {
                       log.info("初审驳回");
                       history.setHtPatentId(patent.getPatentId());
                       history.setHtNewItem("驳回理由 ：" + patent.getPatentRemarks());
                       history.setHtOldItem("专利进度 : 未审核");
                       history.setHtProcess(ProcessEnum.FIRSTCHECKREJECTED.getName());
                       history.setHtOperation(value);
                   }
                   if (patent.getPatentClaim().equals(statusCode_One)) {
                       log.info("复审驳回");
                       history.setHtPatentId(patent.getPatentId());
                       history.setHtNewItem("驳回理由 ：" + patent.getPatentRemarks());
                       history.setHtOldItem("专利进度 : 编写中");
                       history.setHtProcess(ProcessEnum.SECONDCHECKREJECTED.getName());
                       history.setHtOperation(value);
                   }
               }
               if (patent.getSpare().equals(statusCode_One)) {
                   if (patent.getPatentClaim().equals(statusCode_Zero)) {
                       log.info("初审通过");
                       history.setHtPatentId(patent.getPatentId());
                       history.setHtNewItem("专利进度 : 待认领");
                       history.setHtOldItem("专利进度 : 未审核");
                       history.setHtProcess(ProcessEnum.FIRSTCHECKPASS.getName());
                       history.setHtOperation(value);
                   }
                   if (patent.getPatentClaim().equals(statusCode_One)) {
                       log.info("复审通过");
                       history.setHtPatentId(patent.getPatentId());
                       history.setHtNewItem("专利进度 : 待提交");
                       history.setHtOldItem("专利进度 : 编写中");
                       history.setHtProcess(ProcessEnum.SECONDCHECKPASS.getName());
                       history.setHtOperation(value);
                   }
               }
           }
           if (ProcessEnum.UPDATE.getName().equals(value)) {
               history.setHtPatentId(patent.getPatentId());
               history.setHtNewItem(getHistory(patent));
               history.setHtOldItem(getHistory(oldPatent));
               history.setHtProcess(value);
               history.setHtOperation(ProcessEnum.UPDATE.getName());
           }
           if (ProcessEnum.SUBMIT.getName().equals(value)) {
               history.setHtPatentId(patent.getPatentId());
               history.setHtNewItem(getHistory(patent));
               history.setHtOldItem(getHistory(oldPatent));
               history.setHtProcess(value);
               history.setHtOperation(ProcessEnum.SUBMITPATENT.getName());
           }


           historyService.insertHistory(history);
       }catch (Exception e){
           log.info("记录流程历史异常:[{}]",e);
       }
        //获取用户名
        return joinPoint.proceed();
    }

    public static String getHistory(Patent patent) {

        StringBuilder stringBuilder = new StringBuilder();

        if (patent.getApplyNumber() != null) {
            stringBuilder.append("申请号 ：" + patent.getApplyNumber() + "  ");
        }
        if (patent.getCaseNumber() != null) {
            stringBuilder.append("案件文号 ：" + patent.getCaseNumber() + "   ");
        }
        if (patent.getPatentName() != null) {
            stringBuilder.append("专利名 ：" + patent.getPatentName() + "   ");
        }
        if (patent.getPatentRemarks() != null) {
            stringBuilder.append("备注 ：" + patent.getPatentRemarks() + "  ");
        }
        if (patent.getWritePerson() != null) {
            stringBuilder.append("撰写人 ：" + patent.getWritePerson() + "   ");
        }
        if (patent.getCreatePerson() != null) {
            stringBuilder.append("发明人 ：" + patent.getCreatePerson() + "   ");
        }
        return stringBuilder.toString();
    }


}
