package com.sxp.patMag.controller;


import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.PatentPath;
import com.sxp.patMag.service.PatentSelcetService;
import com.sxp.patMag.util.ExcelUtil;
import com.sxp.patMag.util.GeneralResult;
import com.sxp.patMag.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PatentSelcetController
 * @Description: TODO
 * @Author 硕
 * @Date 2019/11/19
 **/

@Controller
@RequestMapping("/patent")
public class PatentSelcetController {




    @Autowired
    PatentSelcetService patentSelcetService;


    /**
     * 专利查询
     * @param patent  专利对象
     * @return
     */
    @RequestMapping(value = "/selectPatentByPatent",method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult selectPatentByPatent(@RequestBody Patent patent){
        if(patent==null){

        }
        List<Patent> list =patentSelcetService.selectPatentByPatent(patent);
        if(list==null){
            return GeneralResult.build(1,"无匹配专利",null);
        }else{
            return GeneralResult.build(0,"成功",list);
        }

    }


    /**
     *管理员专利显示
     */


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

    /*
     *
     * 修改专利状态
     *
*/


    /**
     * 认领人修改
     * @param patent   专利对象
     * @return
     */
    @RequestMapping(value = "/updatePatentToWritePerson",method = RequestMethod.POST)
    @ResponseBody
    public  GeneralResult updatePatentToWritePerson(@RequestBody Patent patent){

        if(patent==null){
            return GeneralResult.build(1,"对象为空",null);
        }

        if(patent.getWritePerson()==null){
            return GeneralResult.build(1,"没有撰写人",null);
        }else if(patent.getWritePerson().length()>100){
            return GeneralResult.build(1,"撰写人字符串过长",null);
        }
        if(patent.getPatentId()==null){
            return GeneralResult.build(1,"没有接收到专利ID",null);
        }else if(patent.getPatentId().length()>100){
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
     *
     * *************   未使用    ********************
     *
     * 查询专利信息
     * @param
     * @return
     */
    @RequestMapping(value = "/selectPatentById",method = RequestMethod.POST)
    @ResponseBody
    public  GeneralResult selectPatentById(@RequestBody Patent patent){
        String patentId = patent.getPatentId();
        System.out.println(patentId);

        Patent list = patentSelcetService.selectPatentById(patentId);
        if(list==null){
            return GeneralResult.build(1,"无匹配专利",null);
        }else{
            return GeneralResult.build(0,"成功",list);
        }
    }



/*    @RequestMapping(value = "/selectPatentToAdmin",method = RequestMethod.POST)
    @ResponseBody
    public  GeneralResult selectPatentToAdmin(){
        List<Patent> list = patentSelcetService.selectPatentToAdmin();
        if(list==null){
            return GeneralResult.build(1,"无匹配专利",null);
        }else{
            return GeneralResult.build(0,"成功",list);
        }
    }*/








    /**
     * 专利文件导出
     *
     * @param    专利
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "/patentExeclOut",method = RequestMethod.GET)
    @ResponseBody
    public GeneralResult patentExeclOut(@RequestBody PatentPath patent) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {


         String path = patent.getPath();
        boolean flag = false;
        System.out.println(path);
        //数据校验
        if(patent==null){
            return GeneralResult.build(1,"对象为空",null);
        }


        /*if(patent.getApplyNumber()==null){
            return GeneralResult.build(1,"没有申请号",null);
        }else if(patent.getApplyNumber().length()>100){
            return GeneralResult.build(1,"申请号过长",null);
        }


        if(patent.getApplyTime()==null){
            return GeneralResult.build(1,"没有申请时间",null);
        }else if(patent.getApplyTime().length()>100){
            return GeneralResult.build(1,"申请时间过长",null);
        }


        if(patent.getPatentName()==null){
            return GeneralResult.build(1,"没有专利名称",null);
        }else if(patent.getPatentName().length()>100){
            return GeneralResult.build(1,"专利名称过长",null);
        }*/



        //处理异常
        try {
            flag =  patentSelcetService.export(patent,path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回数据
        if(!flag){
            return GeneralResult.build(1,"无匹配专利",null);
        }else{
            return GeneralResult.build(0,"成功",null);
        }

    }

}
