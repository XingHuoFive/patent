package com.sxp.patMag.service.serviceImpl;

import com.sxp.patMag.dao.IndicatorMapper;
import com.sxp.patMag.entity.Indicator;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.service.IndicatorService;
import com.sxp.patMag.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.service.serviceImpl
 * @ClassName: IndicatorServiceImpl
 * @date 2019/11/19 19:23
 */
@Service
public class IndicatorServiceImpl implements IndicatorService {

    @Autowired
    private IndicatorMapper indicatorMapper;

    @Override
    public List<Patent> list() {
        List<Patent> patentList = indicatorMapper.getPatentList();
        System.out.println(patentList);
        System.out.println(patentList.get(0).getIndicatorList());
        return indicatorMapper.getPatentList();
    }

    @Override
    public List<Patent> listByPatent(Patent patent) {
        return indicatorMapper.getPatentListByVO(patent);
    }

    @Override
    public int save(Indicator indicator) {
        return indicatorMapper.addIndicator(indicator);
    }

    /**
     * 导出excel
     * @param map 导出条件
     * @return 成功true，失败false
     */
    @Override
    public boolean export(Map<String, Object> map) {
        boolean flag = false;
        List<Patent> list = null;
        Map<String, String> patentMap = (Map<String, String>) map.get("patent");
        if (null == patentMap || patentMap.isEmpty()) {
            list = list();
        } else {
            Patent patent = pretreatmentData(patentMap);
            map.get("patent");
            list = listByPatent(patent);
        }

        List<String> columnNameList = (List<String>) map.get("columnNames");
        if (null == columnNameList || columnNameList.isEmpty()) {
            return false;
        }
        String[] columnNames = new String[columnNameList.size()];
        columnNameList.toArray(columnNames);

        List<String> keyList = (List<String>) map.get("keys");
        if (null == keyList || keyList.isEmpty()) {
            return false;
        }
        String[] keys = new String[keyList.size()];
        keyList.toArray(keys);

        flag = processData(list, columnNames, keys);
        return flag;
    }

    /**
     * 数据预处理，处理复杂查询条件
     * @param map kv对应
     * @return 处理好的类
     */
    private Patent pretreatmentData(Map<String, String> map) {
        Patent patent = new Patent();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            pretreatmentPatent(patent, key, value);
        }
        return patent;
    }

    /**
     * 数据预处理数据添加
     * @param patent 要添加的类
     * @param key key
     * @param value value
     */
    private void pretreatmentPatent(Patent patent, String key, String value) {
        Class clazz = patent.getClass();
        try {
            Method declaredMethod = clazz.getDeclaredMethod("set" + key.substring(0, 1).toUpperCase() + key.substring(1));
            declaredMethod.invoke(value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理数据生成Excel
     * @param list 数据列表
     * @param columnNames 表格表头
     * @param keys 表格表头对应key值列表
     * @return
     */
    private boolean processData(List<Patent> list, String[] columnNames, String[] keys) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        Map<String, Object> mapValue = new HashMap<String, Object>();
        Patent patent = null;
        for (int i = 0; i < list.size(); i++) {
            mapValue.put(keys[0], i + 1 + "");
            for (int j = 1; j < keys.length; j++) {
                patent = list.get(i);
                if ("indicatorName".equals(keys[i])) {
                    mapValue.put(keys[j], patent.getIndicatorList().get(i));
                }
                mapValue.put(keys[j], getParams(keys[j], patent));
            }
        }
        try {
            ExcelUtil.createWorkbook(listmap, keys, columnNames,"test.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 通过反射获取数据
     * @param key 表格表头对应key
     * @param patent 要获取的对象
     * @return 获取到的数据
     */
    private String getParams(String key, Patent patent) {
        Class<? extends Patent> clazz = patent.getClass();
        Method declaredMethod = null;
        try {
            declaredMethod = clazz.getDeclaredMethod("get" + key.substring(0, 1).toUpperCase() + key.substring(1));
            String invoke = (String) declaredMethod.invoke(String.class);
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
