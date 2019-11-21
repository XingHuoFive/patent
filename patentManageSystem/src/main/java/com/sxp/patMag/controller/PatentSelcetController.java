package com.sxp.patMag.controller;


import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.PatentSelcetService;
import com.sxp.patMag.util.ExcelUtil;
import com.sxp.patMag.util.GeneralResult;
import com.sxp.patMag.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        Integer list = patentSelcetService.updatePatentToWritePerson(patent);
        if(list==0){
            return GeneralResult.build(1,"无匹配专利",null);
        }else{
            return GeneralResult.build(0,"成功",list);
        }
    }





    /**
     * 查询专利信息
     * @param patentId  专利ID
     * @return
     */
    @RequestMapping(value = "/selectPatentById",method = RequestMethod.POST)
    @ResponseBody
    public  GeneralResult selectPatentById(@RequestParam("patentId") String patentId){
        System.out.println(patentId);
        Patent list = patentSelcetService.selectPatentById(patentId);
        if(list==null){
            return GeneralResult.build(1,"无匹配专利",null);
        }else{
            return GeneralResult.build(0,"成功",list);
        }
    }

    /**
     * 专利文件导出
     *
     * @param patent   专利
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "/patentExeclOut",method = RequestMethod.POST)
    @ResponseBody
    public GeneralResult patentExeclOut(Patent patent) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        boolean flag = false;

        try {
            flag =  patentSelcetService.export(patent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!flag){
            return GeneralResult.build(1,"无匹配专利",null);
        }else{
            return GeneralResult.build(0,"成功",null);
        }

       /* for (int i = 0; i < listMap.size(); i++) {
            Patent p = listMap.get(i);
            List<String> listString  = new ArrayList<>();
            Field[] field = p.getClass().getDeclaredFields(); //获取实体类的所有属性，返回Field数组
            for(int j=0 ; j<field.length ; j++){ //遍历所有属性
                String name = field[j].getName(); //获取属性的名字
                System.out.println("attribute name:"+name);
                name = name.substring(0,1).toUpperCase()+name.substring(1); //将属性的首字符大写，方便构造get，set方法
                String type = field[j].getGenericType().toString(); //获取属性的类型
            }
        }



*/







      //  ExcelUtil.createWorkbook(listMap);
       // List<Patent> list = patentSelcetService;

    }

}
