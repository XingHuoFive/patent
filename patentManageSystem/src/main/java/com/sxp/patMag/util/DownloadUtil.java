package com.sxp.patMag.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author lhx
 * @PackageName: com.sxp.patMag.util
 * @ClassName: DownloadUtil
 * @date 2019/11/22 14:21
 */
public class DownloadUtil {

    /**
     * 下载文件
     *
     * @param path     文件的位置
     * @param fileName 自定义下载文件的名称
     * @param resp     http响应
     * @param req      http请求
     */
    public static void downloadFile(String path, String fileName, HttpServletResponse resp, HttpServletRequest req) {

        try {
            File file = new File(path);
            /**
             * 中文乱码解决
             */
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            resp.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));

            resp.setContentType("application/octet-stream;charset=utf-8");

            resp.setCharacterEncoding("UTF-8");
            // 设置响应内容的长度
            resp.setContentLength((int) file.length());
            // 输出
            outStream(new FileInputStream(file), resp.getOutputStream());
        } catch (Exception e) {
            System.out.println("执行downloadFile发生了异常：" + e.getMessage());
        } finally {
            File deleteFile = new File(path);
            deleteFile.delete();
        }
    }

    /**
     * 基础字节数组输出
     */
    private static void outStream(InputStream is, OutputStream os) {
        try {
            byte[] buffer = new byte[10240];
            int length = -1;
            while ((length = is.read(buffer)) != -1) {
                os.write(buffer, 0, length);
                os.flush();
            }
        } catch (Exception e) {
            System.out.println("执行 outStream 发生了异常：" + e.getMessage());
        } finally {
            try {
                os.close();
            } catch (IOException e) {
            }
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

}
