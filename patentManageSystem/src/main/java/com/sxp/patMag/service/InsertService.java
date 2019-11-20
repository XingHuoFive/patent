package com.sxp.patMag.service;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.util.GeneralResult;

/**
 * @author Mrs.Wang
 * @create 2019-11-19 18:58
 */
public interface InsertService {

    public GeneralResult InsertPatent(Patent patent);
}
