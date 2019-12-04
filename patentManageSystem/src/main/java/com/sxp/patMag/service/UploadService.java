package com.sxp.patMag.service;

import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
/**
 * @author： Jude
 * @date:2019/11/26
 * @time:11:21
 */

public interface UploadService {

    public GeneralResult insertJbook( Jbook jbook ) throws IOException;

}
