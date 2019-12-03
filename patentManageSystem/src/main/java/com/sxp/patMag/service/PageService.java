package com.sxp.patMag.service;

import com.sxp.patMag.entity.DataGridResult;
import com.sxp.patMag.entity.Patent;

public interface PageService {

    public DataGridResult getItemList(Integer page, Integer rows, Patent patent);


}
