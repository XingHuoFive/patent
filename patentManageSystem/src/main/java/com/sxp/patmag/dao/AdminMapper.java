package com.sxp.patmag.dao;

import com.sxp.patmag.entity.Jbook;
import com.sxp.patmag.entity.Patent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author： Guofengzhang
 * Date:2019/11/20
 * Time:9:37
 */
@Mapper
public interface AdminMapper {
    /**
     * 审核新建的专利
     *
     * @param patent 专利对象
     * @return 修改结果
     */
    boolean checkPatent(Patent patent);

    /**
     * 根据专利id查询它所有的文件
     *
     * @param patentId 专利id
     * @return 文件地址
     */
    List<Jbook> selectAllFilesByPatentId(@Param("patentId") String patentId);

    /**
     * 查询专利的通过与否状态
     *
     * @param patentId 专利id
     * @return 状态值
     */
    String selectSpareOfPatent(String patentId);

}
