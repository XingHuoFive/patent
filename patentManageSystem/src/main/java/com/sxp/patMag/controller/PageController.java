package com.sxp.patMag.controller;

import com.sxp.patMag.entity.DataGridResult;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.exception.ServiceException;
import com.sxp.patMag.service.PageService;
import com.sxp.patMag.service.PatentService;
import com.sxp.patMag.util.GeneralResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author： Jude
 * @date:2019/12/3
 * @time:9:32
 * @return 专利分页
 */

@RestController
@RequestMapping("/page")
public class PageController {

        @Autowired
        private PageService pageService;

        @PostMapping("/selectPatentByPage/{rows}/{page}")
        @ResponseBody
        public GeneralResult selectPatentByPage(@RequestBody Patent patent , @PathVariable("rows") Integer rows,@PathVariable("page") Integer page  ) {
            GeneralResult generalResult = new GeneralResult();
            try {
                if (null == rows || "".equals(rows)) {
                    return GeneralResult.build(1,"行数为空，无法查询");
                }
                if (null == page || "".equals(page)) {
                    return GeneralResult.build(1,"页数为空，无法查询");
                }

                if (null == patent) {
                    generalResult.setStatus(1);
                    generalResult.setMsg("未找到对应专利");
                    return generalResult;
                }
                DataGridResult result = pageService.getItemList(page,rows,patent);

                generalResult.setStatus(0);
                generalResult.setMsg("第"+page+"页，共"+rows+"行");
                generalResult.setData(result);

            } catch (ServiceException e) {
                generalResult.setStatus(1);
                generalResult.setMsg(e.getMessage());
                return generalResult;
            }
            return generalResult;
        }


}
