package com.sxp.patMag.service;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.util.GeneralResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author： Jude
 * @date:2019/11/26
 * @time:11:21
 */


public interface LoginService {

    GeneralResult login(User user);

    GeneralResult getUserByToken(HttpServletRequest request);

    GeneralResult invalidate(HttpServletRequest request);

}
