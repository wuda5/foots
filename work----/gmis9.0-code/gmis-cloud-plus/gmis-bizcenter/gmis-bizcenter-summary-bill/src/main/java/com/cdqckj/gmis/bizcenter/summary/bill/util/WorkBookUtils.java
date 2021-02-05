package com.cdqckj.gmis.bizcenter.summary.bill.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkBookUtils {

    private static final String TITLE = "title";
    private static final String ENTITY = "entity";
    private static final String DATA = "data";
    private static final String USER_AGENT = "user-agent";
    private static final String UTF_8 = "UTF-8";
    private static final String FIRE_FOX = "FireFox";
    private static final String CONTENT_DISPOSITION = "Content-disposition";
    private static final String ISO_8859_1 = "iso-8859-1";
    private static final String ATTACHMENT_FILENAME = "attachment; filename";

    public static Workbook mutiSheet(List<Map<String, Object>> mapListList){
        return ExcelExportUtil.exportExcel(mapListList, ExcelType.XSSF);
    }

    public static Map<String, Object> createOneSheet(ExportParams exportParams, Class<?> clazz, List<?> data){
        Map<String, Object> map = new HashMap<>();
        map.put(TITLE,exportParams);
        map.put(ENTITY, clazz);
        map.put(DATA,data);
        return map;
    }
    /*
     * 创建一个表格并填充内容
     * 返回map供工作簿使用
     * */
    public static Map<String, Object> createOneSheet(String sheetName,String title,Class<?> clazz,List<?> data){
        ExportParams exportParams = new ExportParams(title,sheetName, ExcelType.XSSF);
        return createOneSheet(exportParams,clazz,data);
    }

    /**
     * 将数据存储进response,调用接口就能下载文件
     */
    public static  void dataToResponse(HttpServletRequest request, HttpServletResponse response, File file, String fileName) throws IOException {
        OutputStream out = null;
        FileInputStream in = null;
        try {
            // 1.读取要下载的内容
            in = new FileInputStream(file);
            // 2. 告诉浏览器下载的方式以及一些设置
            // 解决文件名乱码问题，获取浏览器类型，转换对应文件名编码格式，IE要求文件名必须是utf-8, firefox要求是iso-8859-1编码
            String agent = request.getHeader(USER_AGENT);
            if (agent.contains(FIRE_FOX)) {
                fileName = new String(fileName.getBytes(UTF_8), ISO_8859_1);
            } else {
                fileName = URLEncoder.encode(fileName, UTF_8);
            }
            // 设置下载文件的mineType，告诉浏览器下载文件类型
            String mineType = request.getServletContext().getMimeType(fileName);
            response.setContentType(mineType);
            // 设置一个响应头，无论是否被浏览器解析，都下载
            response.setHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + fileName);
            // 将要下载的文件内容通过输出流写到浏览器
            out = response.getOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * 设置文件路径 && 保证文件对象的正确打开
     */
    public static  File createDatafile(String fileDownloadTmpPath, String fileName) throws IOException {
        File dir = new File(fileDownloadTmpPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String resource = fileDownloadTmpPath + fileName;
        File myFile = new File(resource);//创建File对象，参数为String类型，表示目录名
        //判断文件是否存在，如不存在则调用createNewFile()创建新目录，否则跳至异常处理代码
        if (!myFile.exists()) {
            myFile.createNewFile();
        }
        return myFile;
    }

}