package com.sxp.patMag.util;
import com.alibaba.fastjson.JSON;
import com.sxp.patMag.annotation.Monitor;
import com.sxp.patMag.entity.History;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
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

@Aspect
@Component
public class HistoryAOP {
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
        System.out.println("开始操作历史记录");
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
        //时间格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        history.setHtDate(date);
        history.setHtId(UUID.getUUID());
        history.setHtUserId(user.getUserId());
           System.out.println(params);
           //解析参数中的patent 成对象
        Patent patent  = JSON.parseObject( params.substring(1,params.length()-1), Patent.class);
           //从reids获取之前的专利信息
        String old_obj =  redis.get(patent.getPatentId())!=null ? redis.get(patent.getPatentId()).toString() :null;

        System.out.println(old_obj);

        //解析之前的专利为对象
        Patent old_patent = JSON.parseObject( old_obj, Patent.class);;

        //将新的对象放到redis
        redis.set(patent.getPatentId(), JSON.toJSON(patent));
        if (value.equals("新建专利")){
            history.setHtPatentId(patent.getPatentId());

            StringBuilder stringBuilder = new StringBuilder();

            if (patent.getApplyNumber()!=null){
                stringBuilder.append("申请号 ："+patent.getApplyNumber()+"  ");
            }
            if(patent.getCaseNumber()!=null){
                stringBuilder.append("案件文号 ："+patent.getCaseNumber()+"  ");
            }
            if(patent.getPatentName()!=null){
                stringBuilder.append("专利名 ："+patent.getPatentName()+"  ");
            }
            if(patent.getCreatePerson()!=null){
                stringBuilder.append("发明人 ："+patent.getCreatePerson()+"   ");
            }
            if(patent.getApplyPerson()!=null){
                stringBuilder.append("申请人 ："+patent.getApplyPerson()+"   ");
            }

            history.setHtNewItem(stringBuilder.toString());
            history.setHtOldItem("无");
            history.setHtProcess(value);
            history.setHtOperation("新建专利");
        }else if(value.equals("专利认领")){
            history.setHtPatentId(patent.getPatentId());
            history.setHtNewItem("认领人 : "+patent.getWritePerson());
            history.setHtOldItem("认领人 : 无");
            history.setHtProcess(value);
            history.setHtOperation("修改了专利撰写人");
        }else if(value.equals("审核")){
            if (patent.getPatentClaim()=="0"){
                history.setHtPatentId(patent.getPatentId());
                history.setHtNewItem("专利进度 : "+patent.getPatentSchedule());
                history.setHtOldItem("专利进度 : 未审核");
                history.setHtProcess(value);
                history.setHtOperation("初审");
            }
            if (patent.getPatentClaim()=="1"){
                history.setHtPatentId(patent.getPatentId());
                history.setHtNewItem("专利进度 : "+patent.getPatentSchedule());
                history.setHtOldItem("专利进度 : 编写中");
                history.setHtProcess(value);
                history.setHtOperation("复审");
            }
        } else if(value.equals("修改字段")){

      /*  Object obj =  redis.get(patent.getPatentId());
*/
           /* Patent pobj  = JSON.parseObject(  obj.toString() , Patent.class);*/
            //Patent obj  =(Patent) redis.get(patent.getPatentId());

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

            StringBuilder stringBuilder2 = new StringBuilder();

            if (old_patent.getApplyNumber()!=null){
                stringBuilder2.append("申请号 ："+old_patent.getApplyNumber()+"  ");
            }
            if(old_patent.getCaseNumber()!=null){
                stringBuilder2.append("案件文号 ："+old_patent.getCaseNumber()+"  ");
            }
            if(old_patent.getPatentName()!=null){
                stringBuilder2.append("专利名 ："+old_patent.getPatentName()+"  ");
            }
            if(old_patent.getPatentRemarks()!=null){
                stringBuilder2.append("备注 ："+old_patent.getPatentRemarks()+"  ");
            }

            history.setHtPatentId(patent.getPatentId());
            history.setHtNewItem(stringBuilder.toString());
            history.setHtOldItem(stringBuilder2.toString());
            history.setHtProcess(value);
            history.setHtOperation("修改字段");
        }
        /*
        else if(){
            //认领



        }else if(){
            //提交


        }else if(){

        }*/

        historyService.insertHistory(history);
        //获取用户名
        Object proceed = joinPoint.proceed();
        return proceed;
    }

    //@AfterReturning(returning = "ret",pointcut = "getAction()")
    public void doAfter(JoinPoint joinPoint, Object ret) throws Exception {
        Integer result = (Integer) ret;

        System.out.println("方法执行后返回");
        System.out.println(result);

    }


   // @Before("getAction()")
    public void doBefore(JoinPoint joinPoint)  {

        System.out.println("方法执行前");
  /*      //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json

        String params = JSON.toJSONString(args);

        Patent patent = JSON.parseObject(params.substring(1,params.length()-1),Patent.class);

        redis.set(patent.getPatentId(), JSON.toJSON(patent));

        System.out.println(params);
        */
    }


}
