package com.sxp.patMag.service.serviceImpl;

import com.sxp.patMag.dao.PatentSelcetMapper;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.PatentExport;
import com.sxp.patMag.entity.PatentPath;
import com.sxp.patMag.service.PatentSelcetService;
import com.sxp.patMag.util.ExcelUtil;
import com.sxp.patMag.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PatentSelcetServiceImpl implements PatentSelcetService {


    @Autowired
    PatentSelcetMapper  patentSelcetMapper;

    @Autowired
    PatentSelcetService patentSelcetService;

    @Override
    public List<Patent> selectPatentByPatent(Patent patent) {
        return patentSelcetMapper.selectPatentByPatent(patent);
    }


    @Override
    public List<Patent> selectPatentToUser( ) {
        return patentSelcetMapper.selectPatentToUser();
    }

    @Override
    public List<Patent> selectPatentToAdmin() {

        return patentSelcetMapper.selectPatentToAdmin();
    }

    @Override
    public Patent selectPatentById(String patentId){
        return patentSelcetMapper.selectPatentById( patentId);
    }

    @Override
    public Integer updatePatentToWritePerson(Patent patent) {
        return patentSelcetMapper.updatePatentToWritePerson(patent);
    }



//最初下载模板
 /*   @Override
    public Boolean export(Patent patent) throws IOException {
        List<PatentExport> list = patentSelcetMapper.selectPatentByPatentExport(patent);
        String[] columnNames={"编号", "专利名称",   "案件文号",    "申请号",    "专利进度",        "申请日",   "发明人中文名称", "撰写人"};
        String[] keys = {"number", "patentName", "caseNumber", "applyNumber","patentSchedule", "applyTime", "createPerson", "writePerson"};
        Boolean flag = execl(list,columnNames,keys);
        return flag;
    }*/
//弹出下载框
   @Override
    public Boolean export(PatentPath patent , HttpServletResponse response) throws IOException {
        List<PatentExport> list = patentSelcetMapper.selectPatentByPatentExport(patent);
        String[] columnNames={"编号", "专利名称",   "案件文号",    "申请号",    "专利进度",        "申请日",   "发明人中文名称", "撰写人"};
        String[] keys = {"number", "patentName", "caseNumber", "applyNumber","patentSchedule", "applyTime", "createPerson", "writePerson"};
        Boolean flag = execl(list,columnNames,keys,response);//execl(list,columnNames,key,response);
        return flag;
    }

/*
//传入下载地址
     @Override
     public Boolean export(PatentPath patent , String path) throws IOException {
         List<PatentExport> list = patentSelcetMapper.selectPatentByPatentExport(patent);
         String[] columnNames={"编号", "专利名称",   "案件文号",    "申请号",    "专利进度",        "申请日",   "发明人中文名称", "撰写人"};
         String[] keys = {"number", "patentName", "caseNumber", "applyNumber","patentSchedule", "applyTime", "createPerson", "writePerson"};
         Boolean flag = execl(list,columnNames,keys,path);//execl(list,columnNames,key,response);
         return flag;
     }
*/



//最初下载模板
/*    @Override
    public  Boolean execl(List<PatentExport> list, String[] columnNames, String[] keys ) throws IOException {

        List<Map<String, Object>>  listMap = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listMap.add(map);

        //反射获取对象所有属性
        for (PatentExport patent: list) {
            try {
                //添加数据
                getFile(patent,listMap);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return false;
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                return false;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }
        System.out.println(listMap.toString());
        ExcelUtil.createWorkbook(listMap,keys,columnNames,"wangshuo.xlsx");


        return  true;
    }*/



//弹窗下载
//    @Override
//    public  Boolean execl(List<PatentExport> list, String[] columnNames, String[] keys , HttpServletResponse response) throws IOException {
//
//        List<Map<String, Object>>  listMap = new ArrayList<>();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("sheetName", "sheet1");
//        listMap.add(map);
//
//        //反射获取对象所有属性
//        for (PatentExport patent: list) {
//            try {
//                //添加数据
//                getFile(patent,listMap);
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//                return false;
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//                return false;
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//        System.out.println(listMap.toString());
//        ExcelUtil.createWorkbook(listMap,keys,columnNames,response);
//
//        return  true;
//    }

//下载地址
    @Override
    public  Boolean execl(List<PatentExport> list, String[] columnNames, String[] keys , HttpServletResponse response) throws IOException {

        List<Map<String, Object>>  listMap = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listMap.add(map);

        //反射获取对象所有属性
        for (PatentExport patent: list) {
            try {
                //添加数据
                getFile(patent,listMap);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return false;
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                return false;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }
        System.out.println(listMap.toString());
        ExcelUtil.createWorkbook(listMap,keys,columnNames,response);

        return  true;
    }



    /**
     * 遍历对象
     * @param patent
     * @param listMap
     */
    public void  getFile(PatentExport patent,List<Map<String ,Object>> listMap) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[]fields = patent.getClass().getDeclaredFields();
        Map<String, Object> map = new HashMap<String, Object>();;
        //进行遍历

        for (Field field : fields) {
            //获取属性名字
            String name=field.getName();
            //获取属性类型
            String type = field.getGenericType().toString();
            //获取get方法
            Method method= null;//获取属性值
            method = patent.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
            String value = (String) method.invoke(patent);
            if (StringUtils.isNotEmpty(value)) {

                map.put(name, value);
            }
        }
        listMap.add(map);
    }

}
