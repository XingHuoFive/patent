package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Indicator;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.IndicatorService;
import com.sxp.patMag.util.ExcelUtil;
import com.sxp.patMag.util.GeneralResult;
import com.sxp.patMag.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.controller
 * @ClassName: IndicatorController
 * @date 2019/11/19 19:25
 */
@RestController
@RequestMapping("/indicator")
public class IndicatorController {

    @Autowired
    private IndicatorService indicatorService;

    @GetMapping("/export")
    public GeneralResult export(@RequestBody Patent patent) {
        GeneralResult generalResult = listByPatent(patent);
        if (generalResult.getStatus() != 1) {
            return generalResult;
        }
        indicatorService.export((List<Patent>)generalResult.getData());
        return generalResult;
    }

    @GetMapping("/list")
    public GeneralResult list() {
        GeneralResult generalResult = new GeneralResult();
        List<Patent> list = indicatorService.list();
        if (null == list) {
            generalResult.setStatus(-1);
            generalResult.setMsg("失败");
            return generalResult;
        }
        generalResult.setStatus(1);
        generalResult.setMsg("成功");
        generalResult.setData(list);
        return generalResult;
    }

    @PostMapping("/list")
    public GeneralResult listByPatent(@RequestBody Patent patent) {
        GeneralResult generalResult = new GeneralResult();
        if (patent == null) {
            generalResult.setStatus(-1);
            generalResult.setMsg("查询内容为空");
            return generalResult;
        }
        List<Patent> patents = indicatorService.listByPatentId(patent);
        if (null == patents) {
            generalResult.setStatus(-1);
            generalResult.setMsg("失败");
            return generalResult;
        }
        generalResult.setStatus(1);
        generalResult.setMsg("成功");
        generalResult.setData(patents);
        return generalResult;
    }

    @PostMapping("/save")
    public GeneralResult save(@RequestBody Indicator indicator) {
        GeneralResult generalResult = new GeneralResult();
        if (null == indicator) {
            generalResult.setStatus(-1);
            generalResult.setMsg("指标内容不能为空");
            return generalResult;
        }

        if (null == indicator.getPatentId() || "".equals(indicator.getPatentId())) {
            generalResult.setStatus(-1);
            generalResult.setMsg("指标对应专利id不能为空");
            return generalResult;
        }

        if (null == indicator.getIndicatorName() || "".equals(indicator.getIndicatorName())) {
            generalResult.setStatus(-1);
            generalResult.setMsg("指标命不能为空");
            return generalResult;
        }

        indicator.setIndicatorId(UUID.getUUID());
        int save = indicatorService.save(indicator);
        if (save <= 0) {
            generalResult.setStatus(-1);
            generalResult.setMsg("添加失败");
            return generalResult;
        }
        generalResult.setStatus(1);
        generalResult.setMsg("成功");
        return generalResult;
    }

}
