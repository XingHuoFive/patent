package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Indicator;
import com.sxp.patMag.service.IndicatorService;
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

    @GetMapping("/list")
    public GeneralResult list() {
        GeneralResult generalResult = new GeneralResult();
        List<Indicator> list = indicatorService.list();
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

    @GetMapping("/list/{patentId}")
    public GeneralResult listByPatentId(@PathVariable String patentId) {
        GeneralResult generalResult = new GeneralResult();
        if (patentId == null || "".equals(patentId)) {
            generalResult.setStatus(-1);
            generalResult.setMsg("专利id为空");
            return generalResult;
        }
        List<Indicator> indicators = indicatorService.listByPatentId(patentId);
        if (null == indicators) {
            generalResult.setStatus(-1);
            generalResult.setMsg("失败");
            return generalResult;
        }
        generalResult.setStatus(1);
        generalResult.setMsg("成功");
        generalResult.setData(indicators);
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
