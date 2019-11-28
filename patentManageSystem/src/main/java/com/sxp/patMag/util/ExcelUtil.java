package com.sxp.patMag.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.util
 * @ClassName: ExcelUtil
 * @date 2019/11/20 9:32
 */
public class ExcelUtil {



    /**
     * 创建excel文档，
     *
     * @param list        数据
     * @param keys        list中map的key数组集合
     * @param columnNames excel的列名
     * @param filePath    要生成Excel的路径及文件名
     */
    public static void createWorkbook(List<Map<String, Object>> list, String[] keys, String[] columnNames, String filePath) throws IOException {
        if (filePath == null) {
            return;
        }
        //创建工作簿
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        //创建sheet
        Sheet sheet = workbook.createSheet(list.get(0).get("sheetName").toString());
        //创建行
        Row row = sheet.createRow(0);
        //设置列名
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
        }
        //设置每行每列的值
        for (int i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow(i);
            // 在row行上创建一个方格
            for (int j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                if(j == 0){
                    cell.setCellValue(i);
                }
            }
        }
        OutputStream os = new FileOutputStream(filePath);
        workbook.write(os);
        os.flush();
        os.close();
    }


    /**
     * 王硕版导出
     * @param list
     * @param keys
     * @param columnNames
     * @param response
     * @throws IOException
     */
    public static void createWorkbook(List<Map<String, Object>> list, String[] keys, String[] columnNames, /*String filePath*/ HttpServletResponse response) throws IOException {
    /*    if (filePath == null) {
            return;
        }*/
        //创建工作簿
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        //创建sheet
        Sheet sheet = workbook.createSheet(list.get(0).get("sheetName").toString());
        //创建行
        Row row = sheet.createRow(0);
        //设置列名
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
        }
        //设置每行每列的值
        for (int i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow(i);
            // 在row行上创建一个方格
            for (int j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                if(j == 1){
                    cell.setCellValue(i);
                }
                //在方格上添加数据
                cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
            }
        }
        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/octet-stream");
        //设置导出Excel的名称
        response.setHeader("Content-disposition", "attachment;filename=" + "wangshuo.xlsx");
        //刷新缓冲
        response.flushBuffer();
        //workbook将Excel写入到response的输出流中，供页面下载该Excel文件
        workbook.write(response.getOutputStream());
       // workbook.write(os);
      //  os.flush();
        workbook.close();
    }











    /**
     * Excel表格输出下载界面
     *
     * @param response    HttpServletResponse对象
     * @param excelData   Excel表格的数据，封装为List<List<String>>
     * @param sheetName   sheet的名字
     * @param fileName    导出Excel的文件名
     * @param columnWidth Excel表格的宽度，建议为15
     * @throws IOException 抛IO异常
     */
       public static void exportExcel(HttpServletResponse response,
                                   List<List<String>> excelData,
                                   String sheetName,
                                   String fileName,
                                   int columnWidth) throws IOException {

        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格，设置表格名称
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //设置表格列宽度
        sheet.setDefaultColumnWidth(columnWidth);
        //写入List<List<String>>中的数据
        int rowIndex = 0;
        for (List<String> data : excelData) {
            //创建一个row行，然后自增1
            HSSFRow row = sheet.createRow(rowIndex++);
            //遍历添加本行数据
            for (int i = 0; i < data.size(); i++) {
                //创建一个单元格
                HSSFCell cell = row.createCell(i);
                //创建一个内容对象
                HSSFRichTextString text = new HSSFRichTextString(data.get(i));
                //将内容对象的文字内容写入到单元格中
                cell.setCellValue(text);
            }
        }
        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/octet-stream");
        //设置导出Excel的名称
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        //刷新缓冲
        response.flushBuffer();
        //workbook将Excel写入到response的输出流中，供页面下载该Excel文件
        workbook.write(response.getOutputStream());
        //关闭workbook
        workbook.close();
    }








    /**
     * 读取excel 第1张sheet （xls和xlsx）
     *
     * @param filePath	excel路径
     * @param columns	列名（表头）
     * @return 读出的数据
     */
    public static List<Map<String, String>> readExcel(String filePath,String[] columns) {
        Sheet sheet = null;
        Row row = null;
        List<Map<String, String>> list = null;
        String cellData = null;
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                wb = new XSSFWorkbook(is);
            } else {
                wb = null;
            }
            if (wb != null) {
                // 用来存放表中数据
                list = new ArrayList<Map<String, String>>();
                // 获取第一个sheet
                sheet = wb.getSheetAt(0);
                // 获取最大行数
                int rownum = sheet.getPhysicalNumberOfRows();
                row = sheet.getRow(0);
                // 获取最大列数
                int colnum = row.getPhysicalNumberOfCells();
                for (int i = 1; i < rownum; i++) {
                    Map<String, String> map = new LinkedHashMap<String, String>();
                    row = sheet.getRow(i);
                    if (row != null) {
                        for (int j = 0; j < colnum; j++) {
                            cellData = row.getCell(j).getRichStringCellValue().getString();
                            map.put(columns[j], cellData);
                        }
                    } else {
                        break;
                    }
                    list.add(map);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
