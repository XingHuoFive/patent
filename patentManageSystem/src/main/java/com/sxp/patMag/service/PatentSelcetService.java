package com.sxp.patMag.service;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.PatentExport;
import com.sxp.patMag.entity.PatentVO;
import com.sxp.patMag.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface PatentSelcetService {
    /**
     * 根据专利条件查询
     * @param patent 专利
     * @return
     */
    List<Patent> selectPatentByPatent(Patent patent);

    /**
     * 查询未被认领专利
     * @return
     */
    List<Patent> selectPatentToUser();

    /**
     *  查询所有专利
     * @return
     */
    List<Patent>  selectPatentToAdmin();


    /**
     * 根据专利ID查询  专利信息
     * @param patentId
     * @return
     */
    Patent selectPatentById(String patentId);


    /**
     * 修改认领人
     * @param patent
     * @return
     */
    Integer updatePatentToWritePerson(Patent patent);


    /**
     *
     * @param user
     * @return
     */
    PatentVO selectPatentMessage(User user);
/**
     * 设置excel  专利
     * @param list
     * @param columnNames
     * @param keys
     * @return
     * @throws IOException
     *//*

    Boolean execl(List<PatentExport> list, String[] columnNames, String[] keys ) throws IOException ;
*/



    /**
     * 设置excel  专利
     * @param list
     * @param columnNames
     * @param keys
     * @param response
     * @return
     * @throws IOException
     */
    Boolean execl(List<PatentExport> list, String[] columnNames, String[] keys, HttpServletResponse response ) throws IOException ;


/*    *//**
     *
     * @param patent
     * @return
     * @throws IOException
     *//*
    Boolean export(Patent patent) throws IOException;*/


    /**
     *
     * @param patent
     * @return
     * @throws IOException
     */
    Boolean export(PatentVO patent, HttpServletResponse response) throws IOException;
}
