package com.sxp.patMag.service;

import com.sxp.patMag.entity.Patent;

public interface PatentService {

    public Patent selectById(String patentId);
}
