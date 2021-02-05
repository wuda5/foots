package com.cdqckj.gmis.common.utils;

import feign.Response;
import org.springframework.http.HttpHeaders;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ExportUtil {

    public static void exportExcel(Response response, HttpServletResponse httpResponse, String fileName) throws IOException {
        Response.Body body = response.body();
        InputStream inputStream = body.asInputStream();
        // 构建输出流，将输入流写入到输出流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        while (true) {
            int len = inputStream.read(buf);
            if (len < 0) {
                break;
            }
            bos.write(buf, 0, len);
        }
        byte[] by = bos.toByteArray();
        System.err.println("接收到的文件大小为：" + by.length);
        //设置响应头
        HttpHeaders heads = new HttpHeaders();
        heads.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + new String((fileName+".xls").getBytes(), "ISO8859-1"));
        heads.add(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel;charset=utf-8");
        ServletOutputStream outputStream = httpResponse.getOutputStream();
        outputStream.write(by);
        outputStream.flush();
        outputStream.close();
        // -----test-为本地测试下载文件用---
        File file =new File("F:/"+fileName+".xlsx");
        OutputStream out=new FileOutputStream(file,true);
        out.write(by);
        out.close();
    }
}
