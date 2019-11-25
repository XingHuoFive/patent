package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Indicator;
import com.sxp.patMag.entity.IndicatorExport;
import com.sxp.patMag.service.IndicatorService;
import com.sxp.patMag.util.CheckOut;
import com.sxp.patMag.util.GeneralResult;
import com.sxp.patMag.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("/get/{indicatorId}")
    public GeneralResult getById(@PathVariable String indicatorId) {
        GeneralResult generalResult = new GeneralResult();
        if (null == indicatorId || "".equals(indicatorId) || indicatorId.length() != 32) {
            generalResult.setStatus(1);
            generalResult.setMsg("id有误，无法获取");
            return generalResult;
        }

        IndicatorExport indicator = indicatorService.getById(indicatorId);
        if (null == indicator) {
            generalResult.setStatus(0);
            generalResult.setMsg("无匹配数据，查询失败");
            return generalResult;
        }
        generalResult.setStatus(0);
        generalResult.setMsg("查询成功");
        generalResult.setData(indicator);
        return generalResult;
    }

    @PostMapping("/export")
    public GeneralResult export(@RequestBody IndicatorExport indicatorExport, HttpServletResponse resp, HttpServletRequest req) {
        GeneralResult generalResult = new GeneralResult();
        if (null == indicatorExport) {
            generalResult.setStatus(1);
            generalResult.setMsg("导出失败");
            return generalResult;
        }
        if (!CheckOut.checkOutIndicatorSelect(indicatorExport)) {
            generalResult.setStatus(1);
            generalResult.setMsg("失败");
            return generalResult;
        }
        boolean export = indicatorService.export(indicatorExport, resp, req);
        if (!export) {
            generalResult.setStatus(1);
            generalResult.setMsg("导出失败");
            return generalResult;
        }
        generalResult.setStatus(0);
        generalResult.setMsg("导出成功");
        return generalResult;
    }

    @GetMapping("/list")
    public GeneralResult list() {
        GeneralResult generalResult = new GeneralResult();
        List<IndicatorExport> list = indicatorService.list();
        if (null == list) {
            generalResult.setStatus(1);
            generalResult.setMsg("失败");
            return generalResult;
        }
        generalResult.setStatus(0);
        generalResult.setMsg("成功");
        generalResult.setData(list);
        return generalResult;
    }

    @PostMapping("/list")
    public GeneralResult listByPatent(@RequestBody IndicatorExport indicatorExport) {
        GeneralResult generalResult = new GeneralResult();
        if (indicatorExport == null) {
            generalResult.setStatus(1);
            generalResult.setMsg("查询内容为空");
            return generalResult;
        }
        List<IndicatorExport> patents = indicatorService.listByPatent(indicatorExport);
        if (null == patents) {
            generalResult.setStatus(1);
            generalResult.setMsg("失败");
            return generalResult;
        }
        if (!CheckOut.checkOutIndicatorSelect(indicatorExport)) {
            generalResult.setStatus(1);
            generalResult.setMsg("失败");
            return generalResult;
        }
        generalResult.setStatus(0);
        generalResult.setMsg("成功");
        generalResult.setData(patents);
        return generalResult;
    }

    @PostMapping("/save")
    public GeneralResult save(@RequestBody Indicator indicator) {
        GeneralResult generalResult = new GeneralResult();
        if (null == indicator) {
            generalResult.setStatus(1);
            generalResult.setMsg("指标内容不能为空");
            return generalResult;
        }

        if (null == indicator.getPatentId() || "".equals(indicator.getPatentId())) {
            generalResult.setStatus(1);
            generalResult.setMsg("指标对应专利id不能为空");
            return generalResult;
        }

        if (null == indicator.getIndicatorName() || "".equals(indicator.getIndicatorName())) {
            generalResult.setStatus(1);
            generalResult.setMsg("指标命不能为空");
            return generalResult;
        }

        indicator.setIndicatorId(UUID.getUUID());
        int save = indicatorService.save(indicator);
        if (save <= 0) {
            generalResult.setStatus(1);
            generalResult.setMsg("添加失败");
            return generalResult;
        }
        generalResult.setStatus(0);
        generalResult.setMsg("成功");
        return generalResult;
    }

}
