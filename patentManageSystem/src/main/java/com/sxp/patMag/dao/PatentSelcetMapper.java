package com.sxp.patMag.dao;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.PatentExport;
import com.sxp.patMag.entity.PatentVO;
import com.sxp.patMag.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface PatentSelcetMapper {
    List<Patent> selectPatentByPatent(Patent patent);
    List<Patent> selectPatentToUser( );
    PatentVO selectPatentById(String patentId);
    Integer updatePatentToWritePerson(Patent patent);
    List<PatentExport> selectPatentByPatentExport(PatentVO patent);
    List<Patent>  selectPatentToAdmin();
    List<PatentVO> selectPatentMessage(User user);

    String selectPatentSchedule(Patent patent);


}
