package com.sxp.patMag.service.serviceImpl;

import com.sxp.patMag.annotation.Monitor;
import com.sxp.patMag.dao.AdminMapper;
import com.sxp.patMag.entity.Jbook;
import com.sxp.patMag.entity.LogPo;
import com.sxp.patMag.entity.Patent;
import com.sxp.patMag.entity.User;
import com.sxp.patMag.service.AdminService;
import com.sxp.patMag.service.PatentService;
import com.sxp.patMag.util.GeneralResult;
import com.sxp.patMag.util.WeLogFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author： Guofengzhang
 * Date:2019/11/20
 * Time:9:37
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;
    @Autowired
    private PatentService tbPatentService;

    /**
     * 审核新建的专利
     *
     * @param patent 专利
     * @return 修改结果
     */
    @Override
    @Monitor("审核")
    public GeneralResult checkPatent(Patent patent) {
        System.out.println(patent);
        if (patent == null) {
            return GeneralResult.build(1, "校验不通过");
        }
        String patentSpare = patent.getSpare();
        String pClaim = patent.getPatentClaim();
        String patentId = patent.getPatentId();
        if (patentId == null) {
            return GeneralResult.build(1, "id为空!");
        }
        if (patentId.length() > 32) {
            return GeneralResult.build(1, "id超长!");
        }
        if (patentSpare == null || patentSpare.length() == 0) {
            return GeneralResult.build(1, "通过字段参数为空");
        }
        if (pClaim == null || pClaim.length() == 0) {
            return GeneralResult.build(1, "认领字段参数为空");
        }
        if (pClaim.length() > 1) {
            return GeneralResult.build(1, "认领字段参数超长");
        }
        if (patentSpare.trim().length() > 1) {
            return GeneralResult.build(1, "通过字段参数超长");
        }
        boolean check = true;
        check = Character.isDigit(patentSpare.charAt(0));
        if (check == false) {
            return GeneralResult.build(1, "参数包含除数字之外的其他字母");
        }
        check = true;
        for (int i = 0; i < pClaim.length(); i++) {
            check = Character.isDigit(pClaim.charAt(i));
            if (check == false) {
                return GeneralResult.build(1, "参数包含除数字之外的其他字母");
            }
        }
        String patentRemarks = patent.getPatentRemarks();
        if (patentRemarks == null || "".equals(patentRemarks)) {
            patent.setPatentRemarks("你的提交中有内容不符合专利局标准!");
        }
        int patentSpareInt = Integer.parseInt(patentSpare);
        int patentClaimInt = Integer.parseInt(pClaim);
        // 如果该专利通过审核，并且是还没有被认领，就把他的进度修改成待认领状态
        if (patentSpareInt == 1 && patentClaimInt == 0) {
            patent.setPatentSchedule("待认领");
        }
        // 如果该专利通过审核，并且已经被认领了，就把他的进度修改成待提交状态
        if (patentSpareInt == 1 && patentClaimInt == 1) {
            patent.setPatentSchedule("待提交");
        }
        // 如果该专利审核没通过，就将它的进度修改成未通过
        if (patentSpareInt == 0 && patentClaimInt == 0) {
            patent.setPatentSchedule("未通过");
        }
        // 如果该专利审核没通过，就将它的进度修改成编写中
        if (patentSpareInt == 0 && patentClaimInt == 1) {
            patent.setPatentSchedule("编写中");
            tbPatentService.noSubmitPatent(patent);
        }


        boolean flag = adminMapper.checkPatent(patent);
        String spareNum = adminMapper.selectSpareOfPatent(patentId);
        int spareNumInt = Integer.parseInt(spareNum);
        if (spareNum == null) {
            spareNum = "0";
        }
        if (flag == true && spareNumInt == 1) {
            return GeneralResult.build(0, "审核通过!");
        }
        if (flag == true && spareNumInt == 0) {
            return GeneralResult.build(1, "审核不通过!");
        }
        return GeneralResult.build(1, "其他错误!");
    }

//    /**
//     * 根据专利id查询它所有的文件
//     *
//     * @param patentId 专利id
//     * @return 文件地址
//     */
//    @Override
//    public GeneralResult selectAllFilesByPatentId(String patentId) {
//        if (patentId == null || patentId.length() > 16) {
//            return GeneralResult.build(1, "该专利id无效");
//        }
//        boolean check = true;
//        for (int i = 0; i < patentId.length(); i++) {
//            check = Character.isDigit(patentId.charAt(i));
//            if (check == false) {
//                return GeneralResult.build(1, "校验出错");
//            }
//        }
//        List<Jbook> list = adminMapper.selectAllFilesByPatentId(patentId);
//        if (list == null || list.size() == 0) {
//            //返回登录失败
//            return GeneralResult.build(1, "fail");
//        }
//        return GeneralResult.build(0, "success", list);
//    }

    /**
     * 管理员读取日志
     *
     * @return 日志列表
     */
    @Override
    public GeneralResult readLogFile(String role, HttpServletResponse response) {
        String dir = WeLogFile.readLog();
        if (dir == null || dir.length() == 0) {
            //返回查询失败
            return GeneralResult.build(1, "fail", "查询失败");
        }
        if ("0".equals(role)) {
            return GeneralResult.build(1, "fail", "您不是管理员，无法查看日志!");
        }
        if ("1".equals(role)) {
            File file = new File(dir);
            if (file.exists()) {
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                response.setCharacterEncoding("utf-8");
                response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;

                return toArrayByInputStreamReader1(dir);

               /* try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                      return GeneralResult.build(0, "success", "下载成功!");
                } catch (IOException e) {
                    throw new ServiceException(PatentException.EXPORT_ERROR);
                }finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }*/
            }
            return GeneralResult.build(0, "fail", "下载失败");
        }
        return GeneralResult.build(0, "success", "role没收到!");
    }


    public static GeneralResult toArrayByInputStreamReader1(String name) {
        // 使用ArrayList来存储每行读取到的字符串
        List<String> arrayList = new ArrayList<>();
        List<LogPo> logList = new ArrayList<>();
        try {
            File file = new File(name);
            InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
            BufferedReader bf = new BufferedReader(inputReader);
            // 按行读取字符串
            String str;
            int i = 0;
            while ((str = bf.readLine()) != null && i <= 100) {
                arrayList.add(str);
                i++;
            }
            bf.close();
            inputReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对ArrayList中存储的字符串进行处理
        int length = arrayList.size();
        int[] array = new int[length];

        Comparator<LogPo> comparator = new Comparator<LogPo>() {
            @Override
            public int compare(LogPo log1, LogPo log2) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date sd2 = null;
                Date sd1 = null;
                try {
                    sd1 = df.parse(log1.getCreateTime());
                    sd2 = df.parse(log2.getCreateTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return sd1.before(sd2) ? 1 : -1;
            }
        };

        for (int i = 0; i < length; i++) {


            String s = arrayList.get(i);

            String[] one = s.split("--");
/*
            System.out.println("-------------------------------------");
        for (int k =0 ;k<one.length;k++){
            System.out.println(one[k]);
        }
            System.out.println("-------------------------------------");
*/

            LogPo logPo = new LogPo();
            logPo.setCreateTime(one[0]);
            logPo.setUserName(one[2]);

            if (one[3].contains("select") || one[3].contains("get") || one[3].contains("list") || one[3].contains("List")) {
                logPo.setItem("查询" + retString(one[3]));
                logPo.setOperation(one[3].replace(one[3].substring(3, one[3].length() - 1), "****"));
            }
            if (one[3].contains("update")) {
                logPo.setItem("修改" + retString(one[3]));
                logPo.setOperation(one[3].replace(one[3].substring(3, one[3].length() - 1), "****"));
            }
            if (one[3].contains("login")) {
                logPo.setItem("用户登录");
                logPo.setOperation(one[3].replace(one[3].substring(3, one[3].length() - 1), "****"));
            }
            if (one[3].contains("add") || one[3].contains("insert") || one[3].contains("list")) {
                logPo.setItem("新增" + retString(one[3]));
                logPo.setOperation(one[3].replace(one[3].substring(3, one[3].length() - 1), "****"));
            }
            if (one[3].contains("check")) {
                logPo.setItem("审核" + retString(one[3]));
                logPo.setOperation(one[3].replace(one[3].substring(3, one[3].length() - 1), "****"));
            }
            if (one[3].contains("export")) {
                logPo.setItem("导出");
            }
            if (one[3].contains("submit")) {
                logPo.setItem("提交");
            }
            if (one[3].contains("upload")) {
                logPo.setItem("上传文件");
            }
            if (one[3].contains("read")) {
                continue;
            }


            logList.add(logPo);

            // array[i] = s;
        }
       // System.out.println(logList.size());
        // 返回数组
        Collections.sort(logList, comparator);
        List<LogPo> list = null;
        if (logList.size() < 90) {
            list = logList.subList(0, logList.size());
        } else {
            list = logList.subList(0, 90);
        }


        return GeneralResult.build(0, "success", list);
    }

    public static String retString(String str) {
        if (str.contains("History")) {
            return "历史记录";
        }
        if (str.contains("Indicator")) {
            return "指标";
        }
        if (str.contains("Jbook")) {
            return "交底书";
        }
        if (str.contains("Patent")) {
            return "专利";
        }
        return "";
    }

    /**
     * 修改通知状态
     *
     * @param patent 存储目标专利信息
     * @return 操作信息
     */
    @Override
    public GeneralResult updatePatentRemarkView(Patent patent) {
        int flag = adminMapper.updatePatentRemarkView(patent);
        if (flag > 0) {
            return GeneralResult.build(0, "success", "修改成功");
        } else {
            return GeneralResult.build(1, "fail", "修改失败");
        }
    }

    /**
     * 登录后显示通知
     *
     * @param user 登录者信息
     * @return 通知列表
     */
    @Override
    public GeneralResult showPatentNotice( User user) {
        List<Patent> list = adminMapper.selectRemarkViewOfPatent(user);
        if (list != null || list.size() != 0) {
            return GeneralResult.build(0, "success", list);
        } else {
            return GeneralResult.build(1, "fail", "暂时没有通知");
        }
    }
}
