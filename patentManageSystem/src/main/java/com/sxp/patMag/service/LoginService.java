package com.sxp.patMag.service;

import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.util.GeneralResult;
/**
 * @author： Jude
 * @date:2019/11/26
 * @time:11:21
 */

public interface LoginService {

    public GeneralResult login(User user);
    public GeneralResult getUserByToken(String token);


}
