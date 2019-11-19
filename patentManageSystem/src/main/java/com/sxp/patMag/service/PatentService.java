package com.sxp.patMag.service;

import com.sxp.patMag.util.GeneralResult;

/**
 * Author： Jude
 * Date:2019/11/19
 * Time:18:18
 */
public interface PatentService {

    public GeneralResult selectById(String patentId);
}
