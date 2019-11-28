package com.sxp.patMag.util;
import com.alibaba.fastjson.JSON;
import com.sxp.patMag.annotation.Monitor;
import com.sxp.patMag.entity.History;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.enums.ProcessEnum;
import com.sxp.patMag.service.HistoryService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author： Jude
 * @date:2019/11/26
 * @time:11:21
 */


@Aspect
@Component
public class HistoryReflect {
    @Autowired
    private RedisUtil redis ;

    @Autowired
    private HistoryService historyService;


    private User user;

    public void setUser(User user) {
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
     * @param joinPoint
     * @throws IOException
     */


    @Around("getAction()")
    public Object writeHistory(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("佛祖显灵");
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
        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = JSON.toJSONString(args);
        //时间格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        history.setHtDate(date);
        history.setHtId(UUID.getUUID());
        history.setHtUserId(user.getUserId());
        //   System.out.println(params);
           //解析参数中的patent 成对象
        Patent patent  = JSON.parseObject( params.substring(1,params.length()-1), Patent.class);
           //从reids获取之前的专利信息
        String oldObj =  redis.get("Patent" + ":" + patent.getPatentId())!=null ? redis.get("Patent" + ":" +patent.getPatentId()).toString() :null;

       // System.out.println(old_obj);

        //解析之前的专利为对象
        Patent oldPatent = JSON.parseObject( oldObj, Patent.class);;

        //将新的对象放到redis
        redis.set("Patent" + ":" +patent.getPatentId(), JSON.toJSON(patent));
        redis.expire("Patent" + ":" +patent.getPatentId(),  86400);
        if (ProcessEnum.NEW.getName().equals(value)){
            history.setHtPatentId(patent.getPatentId());
            history.setHtNewItem(getHistory(patent));
            history.setHtOldItem("无");
            history.setHtProcess(value);
            history.setHtOperation("新建专利");
        }
        if(ProcessEnum.CLAIM.getName().equals(value)){
            history.setHtPatentId(patent.getPatentId());
            history.setHtNewItem("认领人 : "+patent.getWritePerson());
            history.setHtOldItem("认领人 : 无");
            history.setHtProcess(value);
            history.setHtOperation("修改撰写人");
        }
        if(ProcessEnum.CHECK.getName().equals(value)){
            if (patent.getPatentClaim()=="0"){
                System.out.println("初审");
                history.setHtPatentId(patent.getPatentId());
                history.setHtNewItem("专利进度 : "+patent.getPatentSchedule());
                history.setHtOldItem("专利进度 : 未审核");
                history.setHtProcess("初审");
                history.setHtOperation(value);
            }
            if (patent.getPatentClaim()=="1"){
                System.out.println("复审");
                history.setHtPatentId(patent.getPatentId());
                history.setHtNewItem("专利进度 : "+patent.getPatentSchedule());
                history.setHtOldItem("专利进度 : 编写中");
                history.setHtProcess("复审");
                history.setHtOperation(value);
            }
        }
        if(ProcessEnum.UPDATE.getName().equals(value)){

            history.setHtPatentId(patent.getPatentId());
            history.setHtNewItem(getHistory(patent));
            history.setHtOldItem(getHistory(oldPatent));
            history.setHtProcess(value);
            history.setHtOperation("修改字段");
        }
        historyService.insertHistory(history);
        //获取用户名
        Object proceed = joinPoint.proceed();
        return proceed;
    }
public static String getHistory(Patent patent){

    StringBuilder stringBuilder = new StringBuilder();

    if (patent.getApplyNumber()!=null){
        stringBuilder.append("申请号 ："+patent.getApplyNumber()+"  ");
    }
    if(patent.getCaseNumber()!=null){
        stringBuilder.append("案件文号 ："+patent.getCaseNumber()+ "   ");
    }
    if(patent.getPatentName()!=null){
        stringBuilder.append("专利名 ："+patent.getPatentName()+"   ");
    }
    if(patent.getPatentRemarks()!=null){
        stringBuilder.append("备注 ："+patent.getPatentRemarks()+"  ");
    }
    if(patent.getWritePerson()!=null){
        stringBuilder.append("撰写人 ："+patent.getWritePerson()+"   ");
    }
    if(patent.getCreatePerson()!=null){
        stringBuilder.append("发明人 ："+patent.getCreatePerson()+"   ");
    }
        return stringBuilder.toString();
}


}
