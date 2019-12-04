package com.sxp.patMag.util;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxp.patMag.entity.DataGridResult;
import java.util.List;

/**
 * @author： Jude
 * @date:2019/12/4
 * @time:10:27
 */

public class PageHelperUtil {

    public  static  DataGridResult getItemList(Integer page, Integer rows, List  list) {
        // 设置分页信息
        PageHelper.startPage(page, rows);
        // 取查询结果
        PageInfo  pageInfo = new PageInfo (list);
        DataGridResult res = new DataGridResult();
        res.setTotal(pageInfo.getTotal());
        res.setRows(list);
        // 返回结果
        return res;
    }

}
