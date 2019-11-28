package com.sxp.patMag.controller;

import com.sxp.patMag.entity.Indicator;
import com.sxp.patMag.entity.IndicatorExport;
import com.sxp.patMag.exception.PatentException;
import com.sxp.patMag.exception.ServiceException;
import com.sxp.patMag.service.IndicatorService;
import com.sxp.patMag.util.GeneralResult;
import com.sxp.patMag.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
        try {
            if (null == indicatorId || "".equals(indicatorId) || 32 == indicatorId.length()) {
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
        } catch (ServiceException e) {
            generalResult.setStatus(1);
            generalResult.setMsg(e.getMessage());
            return generalResult;
        }
        return generalResult;
    }

    @PostMapping("/export")
    public GeneralResult export(@RequestBody @Valid IndicatorExport indicatorExport, BindingResult bindingResult, HttpServletResponse resp, HttpServletRequest req) {
        GeneralResult generalResult = new GeneralResult();
        try {
            if(bindingResult.hasErrors()) {
                throw new ServiceException(PatentException.ERROR_PARAME);
            }
            if (null == indicatorExport) {
                generalResult.setStatus(1);
                generalResult.setMsg("导出失败");
                return generalResult;
            }
            String export = indicatorService.export(indicatorExport, resp, req);
            if ("".equals(export)) {
                generalResult.setStatus(1);
                generalResult.setMsg("导出失败");
                return generalResult;
            }
            generalResult.setStatus(0);
            generalResult.setMsg("导出成功");
            generalResult.setData(export);
        } catch (ServiceException e) {
            generalResult.setStatus(1);
            generalResult.setMsg(e.getExceptionEnum().getMessage());
            return generalResult;
        }
        return generalResult;
    }

    @GetMapping("/list")
    public GeneralResult list() {
        GeneralResult generalResult = new GeneralResult();
        try {
            List<IndicatorExport> list = indicatorService.list();
            if (null == list) {
                generalResult.setStatus(1);
                generalResult.setMsg("失败");
                return generalResult;
            }
            generalResult.setStatus(0);
            generalResult.setMsg("成功");
            generalResult.setData(list);
        } catch (ServiceException e) {
            generalResult.setStatus(1);
            generalResult.setMsg(e.getMessage());
            return generalResult;
        }
        return generalResult;
    }

    @PostMapping("/list")
    public GeneralResult listByPatent(@RequestBody @Validated IndicatorExport indicatorExport, BindingResult bindingResult) {
        GeneralResult generalResult = new GeneralResult();
        try {
            if(bindingResult.hasErrors()) {
                throw new ServiceException(PatentException.ERROR_PARAME);
            }
            if (indicatorExport == null) {
                generalResult.setStatus(1);
                generalResult.setMsg("查询内容为空");
                return generalResult;
            }
            List<IndicatorExport> patents = indicatorService.listByPatent(indicatorExport);
            if (null == patents || patents.isEmpty()) {
                generalResult.setStatus(1);
                generalResult.setMsg("失败");
                return generalResult;
            }
            generalResult.setStatus(0);
            generalResult.setMsg("成功");
            generalResult.setData(patents);
        } catch (ServiceException e) {
            generalResult.setStatus(1);
            generalResult.setMsg(e.getExceptionEnum().getMessage());
            return generalResult;
        }
        return generalResult;
    }

}
