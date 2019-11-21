package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.PatentSelcetService;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.List;

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
     *
     * @param patent
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
     * 认领人修改
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
     * 审核状态
     * @param patentId
     * @return
     */


    @RequestMapping(value = "/selectPatentById",method = RequestMethod.POST)
    @ResponseBody
    public  GeneralResult selectPatentById(String patentId){
        Patent list = patentSelcetService.selectPatentById(patentId);
        if(list==null){
            return GeneralResult.build(1,"无匹配专利",null);
        }else{
            return GeneralResult.build(0,"成功",list);
        }
    }

}
