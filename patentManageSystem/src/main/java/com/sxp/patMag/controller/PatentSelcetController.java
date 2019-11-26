package com.sxp.patMag.controller;


import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.PatentVO;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.service.PatentSelcetService;
import com.sxp.patMag.util.GeneralResult;
import com.sxp.patMag.util.JsonUtils;
import com.sxp.patMag.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @ClassName PatentSelcetController
 * @Description: TODO
 * @Author 硕
 * @Date 2019/11/19
 **/

@Controller
@RequestMapping("/patentSelect")
public class PatentSelcetController {

    @Autowired
    PatentSelcetService patentSelcetService;
    @Autowired
    private RedisUtil redis ;
    /**
     * 专利查询
     * @param patent  专利对象
     * @return
     */
    @RequestMapping(value = "/selectPatentByPatent",method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult selectPatentByPatent(@RequestBody Patent patent){
        //System.out.println(patent.getPatentClaim()+"======================");
        GeneralResult generalResult = null;
        if(patent==null){
            return GeneralResult.build(1,"对象为空",null);
        }
        if(patent.getCaseNumber() == null){

        }else if(patent.getCaseNumber().length() > 16){
            return GeneralResult.build(1,"案例号过长",null);
        }

        if(patent.getApplyNumber() == null){

        }else if(patent.getApplyNumber().length() > 16){
            return GeneralResult.build(1,"申请号过长",null);
        }

        if(patent.getPatentName() == null){

        }else if(patent.getPatentName().length() > 150){
            return GeneralResult.build(1,"专利名过长",null);
        }

        if(patent.getApplyTime() == null){

        }else if(patent.getApplyTime().length() > 30){
            return GeneralResult.build(1,"没有符合的申请时间",null);
        }

        List<Patent> list =patentSelcetService.selectPatentByPatent(patent);

        if(list == null){
            return GeneralResult.build(1,"无匹配专利",null);
        }else if(list.isEmpty()){
            return GeneralResult.build(1,"无匹配专利",null);
        } else{
            return GeneralResult.build(0,"成功",list);
        }

    }



    /**
     * 查询未被认领的专利
     * @param
     * @return
     */
    @RequestMapping(value = "/selectPatentToUser",method = RequestMethod.POST)
    @ResponseBody
    public  GeneralResult selectPatentToUser(){
        System.out.println(patentSelcetService.selectPatentToUser());
        List<Patent> list = patentSelcetService.selectPatentToUser();
        if(list==null){
            return GeneralResult.build(1,"无匹配专利",null);
        }else{
            return GeneralResult.build(0,"成功",list);
        }

    }



    /**
     * 修改认领人状态和认领人修改
     * @param patent   专利对象
     * @return
     */
    @RequestMapping(value = "/updatePatentToWritePerson",method = RequestMethod.POST)
    @ResponseBody
    public  GeneralResult updatePatentToWritePerson(@RequestBody Patent patent,HttpServletRequest req){
        String token  =  req.getHeader("data");
        String json = (String) redis.get("UserLogin" + ":" + token);
        if ( json==null||json.length()==0) {
            return GeneralResult.build(1, "无此用户");
        }
        //把json转换成User对象
        User user = JsonUtils.jsonToPojo(json, User.class);
        patent.setWritePerson(user.getUserName());
        System.out.println(patent.toString());
        if(patent==null){
            return GeneralResult.build(1,"对象为空",null);
        }
        if(patent.getPatentId() == null){
            return GeneralResult.build(1,"没有接收到专利ID",null);
        }else if(patent.getPatentId().length() > 50){
            return GeneralResult.build(1,"专利ID字符串过长",null);
        }

        Integer list = patentSelcetService.updatePatentToWritePerson(patent);
        if(list==0){
            return GeneralResult.build(1,"无匹配专利",null);
        }else{
            return GeneralResult.build(0,"成功",list);
        }
    }



    /**
     * *************   未使用    ********************
     * 根据id查询专利信息
     * @param
     * @return
     */
    @RequestMapping(value = "/selectPatentById",method = RequestMethod.POST)
    @ResponseBody
    public  GeneralResult selectPatentById(@RequestBody Patent patent){
        String patentId = patent.getPatentId();
        System.out.println(patentId);

        PatentVO list = patentSelcetService.selectPatentById(patentId);
        if(list == null){
            return GeneralResult.build(1,"无匹配专利",null);
        }else{
            return GeneralResult.build(0,"成功",list);
        }
    }



    /**
     *专利池显示（管理员）
     */
    @RequestMapping(value = "/selectPatentToAdmin",method = RequestMethod.POST)
    @ResponseBody
    public  GeneralResult selectPatentToAdmin(){
        List<Patent> list = patentSelcetService.selectPatentToAdmin();
        if(list == null){
            return GeneralResult.build(1,"无匹配专利",null);
        }else{
            return GeneralResult.build(0,"成功",list);
        }
    }



    /**
     * 专利文件导出
     * @param    patent
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "/patentExeclOut",method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult patentExeclOut(PatentVO patent, HttpServletRequest request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        //String path = "D:\\wangshuo.xlsx";
        String url = "";

        //数据校验
     /*   if(patent == null){
            return GeneralResult.build(1,"对象为空",null);
        }*/

        /*if(patent.getApplyNumber() == null){
            return GeneralResult.build(1,"没有申请号",null);
        }else if(patent.getApplyNumber().length() > 100){
            return GeneralResult.build(1,"申请号过长",null);
        }

        if(patent.getApplyTime() == null){
            return GeneralResult.build(1,"没有申请时间",null);
        }else if(patent.getApplyTime().length() > 100){
            return GeneralResult.build(1,"申请时间过长",null);
        }

        if(patent.getPatentName() == null){
            return GeneralResult.build(1,"没有专利名称",null);
        }else if(patent.getPatentName().length() > 100){
            return GeneralResult.build(1,"专利名称过长",null);
        }*/

        //处理异常
        try {
            url = patentSelcetService.export(patent,request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回数据
        if(url == ""){
            return GeneralResult.build(1,"无匹配专利",null);
        }else{
            return GeneralResult.build(0,"成功",url);
        }


    }



    /**
     *根据用户查询专利显示
     */
    @RequestMapping(value = "/selectPatentMessage",method = RequestMethod.POST)
    @ResponseBody
    public  GeneralResult selectPatentMessage(@RequestBody User user){
        if(user == null){
            return GeneralResult.build(1,"对象为空",null);
        }
        PatentVO list = patentSelcetService.selectPatentMessage(user);
        System.out.println(list.toString());
        if(list == null){
            return GeneralResult.build(1,"无匹配专利",null);
        }else{
            return GeneralResult.build(0,"成功",list);
        }
    }
        /*public void downloadPlan(HttpServletResponse response, HttpServletRequest request) throws IOException{
        OutputStream os = null;
        //注意文件的路径；只有路径正确，才能完成下载；
        String filePath = request.getSession().getServletContext().getRealPath("D:\\");
        System.out.println(filePath+"---------------------");
        File f = new File("D:\\wangshuo.xlsx");
        FileInputStream input = new FileInputStream(f);
        byte[] buffer  = new byte[(int)f.length()];
        int offset = 0;
        int numRead = 0;
        while (offset<buffer.length&&(numRead-input.read(buffer,offset,buffer.length-offset))>=0) {
            offset+=numRead;
        }
        input.close();
        os = response.getOutputStream();
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment;filename="+f.getName());;
        os.write(buffer);
    }*/
}
