package com.cdqckj.gmis.base.utils;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReportUtils {
    // Excel 导出 通过浏览器下载的形式
    public static void exportExcel(HttpServletResponse response, Workbook workbook, String fileName) throws IOException {
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "iso8859-1"));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(response.getOutputStream());
        workbook.write(bufferedOutPut);
        bufferedOutPut.flush();
        bufferedOutPut.close();
        /*FileOutputStream fout = new FileOutputStream("F:/test.xlsx");
        workbook.write(fout);
        fout.flush();
        fout.close();*/
    }

}
