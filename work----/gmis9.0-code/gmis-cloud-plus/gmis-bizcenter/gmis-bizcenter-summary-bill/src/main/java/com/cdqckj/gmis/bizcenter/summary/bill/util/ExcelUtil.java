package com.cdqckj.gmis.bizcenter.summary.bill.util;

import jline.internal.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
public class ExcelUtil {

    public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            //设置浏览器响应头对应的Content-disposition
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            //编码
            response.setCharacterEncoding("UTF-8");
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            Log.error("下载异常，{}",e.getMessage(),e);
        }finally {
            try {
                workbook.close();
            } catch (IOException e) {
                Log.error("workBook关闭异常，{}",e.getMessage(),e);
            }
        }
    }
}
