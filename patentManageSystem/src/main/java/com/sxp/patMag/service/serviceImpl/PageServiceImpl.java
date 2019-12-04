package com.sxp.patMag.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxp.patMag.dao.PatentSelcetMapper;
import com.sxp.patMag.entity.DataGridResult;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.PageService;
import com.sxp.patMag.util.PageHelperUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author： Jude
 * @date:2019/12/3
 * @time:9:32
 */

@Service
public class PageServiceImpl implements PageService {

@Autowired
private PatentSelcetMapper mapper;
    @Override
    public DataGridResult getItemList(Integer page, Integer rows, Patent patent) {
        List  list =mapper.selectPatentByPatent(patent);
        return PageHelperUtil.getItemList(page,rows,list);
    }
}
