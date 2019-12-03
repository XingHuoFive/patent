package com.sxp.patMag.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxp.patMag.dao.PatentSelcetMapper;
import com.sxp.patMag.entity.DataGridResult;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.PageService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author： Jude
 * @date:2019/12/3
 * @time:9:32
 */

public class PageServiceImpl implements PageService {

@Autowired
private PatentSelcetMapper mapper;


    @Override
    public DataGridResult getItemList(Integer page, Integer rows, Patent patent) {
        // 设置分页信息
        PageHelper.startPage(page, rows);

        List<Patent> list =mapper.selectPatentByPatent(patent);
        // 取查询结果
        PageInfo<Patent> pageInfo = new PageInfo<>(list);
        DataGridResult res = new DataGridResult();
        res.setTotal(pageInfo.getTotal());
        res.setRows(list);
        // 返回结果
        return res;
    }


}
