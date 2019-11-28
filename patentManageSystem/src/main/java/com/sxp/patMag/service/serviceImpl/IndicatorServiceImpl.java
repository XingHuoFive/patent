package com.sxp.patMag.service.serviceImpl;

import com.sxp.patMag.dao.IndicatorMapper;
import com.sxp.patMag.entity.Indicator;
import com.sxp.patMag.entity.IndicatorExport;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.IndicatorService;
import com.sxp.patMag.service.PatentService;
import com.sxp.patMag.util.DownloadUtil;
import com.sxp.patMag.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.service.serviceImpl
 * @ClassName: IndicatorServiceImpl
 * @date 2019/11/19 19:23
 */
@Service
public class IndicatorServiceImpl implements IndicatorService {

    @Value("${file.uploadFolder}")
    private String realBasePath;

    @Value("${file.accessPath}")
    private String accessPath;

    @Autowired
    private IndicatorMapper indicatorMapper;

    @Autowired
    private PatentService patentService;

    @Override
    public List<IndicatorExport> list() {
        return patentService.list();
    }

    @Override
    public List<IndicatorExport> listByPatent(IndicatorExport indicatorExport) {
        return patentService.listByPatent(indicatorExport);
    }

    @Override
    public int save(List<Indicator> indicator) {
        return indicatorMapper.addIndicator(indicator);
    }

    /**
     * 导出excel
     * @param indicatorExport 导出条件
     * @return 成功true，失败false
     */

    @Override
    public String export(IndicatorExport indicatorExport, HttpServletResponse resp, HttpServletRequest req) {
        boolean flag = false;
        List<IndicatorExport> patents = patentService.listByPatent(indicatorExport);
        String[] columnNames={"编号", "指标详情", "所属专利", "专利进度", "申请日", "发明人中文名称", "撰写人"};
        String[] keys = {"number", "indicatorName", "caseNumber", "patentSchedule", "applyTime", "createPerson", "writePerson"};
        Date todayDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(todayDate);
        String realPath = realBasePath + today + "/";
        String saveToPath = accessPath + today + "/";
        String filepath = realPath + "indicator.xlsx";
        flag = processData(patents, columnNames, keys, filepath);
        if (flag) {
            return DownloadUtil.downloadByUrl(saveToPath + "indicator.xlsx");
        } else {
            return "";
        }
    }

    /**
     * 根据id获取指标详情
     * @param indicatorId 要获取的指标id
     * @return 获取到的指标详情
     */
    @Override
    public IndicatorExport getById(String indicatorId) {
        return patentService.getById(indicatorId);
    }

    /**
     * 处理数据生成Excel
     * @param list 数据列表
     * @param columnNames 表格表头
     * @param keys 表格表头对应key值列表
     * @param filePath 导路径
     * @return
     */
    private boolean processData(List<IndicatorExport> list, String[] columnNames, String[] keys, String filePath) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        Map<String, Object> mapValue = null;
        IndicatorExport indicatorExport = null;
        for (int i = 0; i < list.size(); i++) {
            mapValue = new HashMap<>();
            indicatorExport = list.get(i);
            mapValue.put(keys[0], i + 1);
            for (int j = 1; j < keys.length; j++) {
                mapValue.put(keys[j], getParams(keys[j], indicatorExport));
            }
            listmap.add(mapValue);
        }
        try {
            ExcelUtil.createWorkbook(listmap, keys, columnNames,filePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 通过反射获取数据
     * @param key 表格表头对应key
     * @param indicatorExport 要获取的对象
     * @return 获取到的数据
     */
    private String getParams(String key, IndicatorExport indicatorExport) {
        Class<? extends IndicatorExport> clazz = indicatorExport.getClass();
        Method declaredMethod = null;
        try {
            declaredMethod = clazz.getDeclaredMethod("get" + key.substring(0, 1).toUpperCase() + key.substring(1));
            String invoke = (String) declaredMethod.invoke(indicatorExport);
            return invoke;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
