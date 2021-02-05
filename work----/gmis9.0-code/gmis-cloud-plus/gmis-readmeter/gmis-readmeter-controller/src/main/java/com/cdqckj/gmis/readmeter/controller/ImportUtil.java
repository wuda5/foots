package com.cdqckj.gmis.readmeter.controller;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ImportUtil {

    public static List<Map<String, Object>> readExcel(InputStream inputStream) throws Exception {
        int sheetnum=0,startrow=0,startcol=0;
        List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(sheetnum);                     //sheet 从0开始
            int rowNum = sheet.getLastRowNum() + 1;                     //取得最后一行的行号
            Map<String, Object> Header=new HashMap<String, Object>();   //表头的下标和内容
            for (int i = startrow; i < rowNum; i++) {                    //行循环开始
                Map<String, Object> map=null;//每一行数据的map
                HSSFRow row = sheet.getRow(i);                             //行
                if(row==null) {
                    continue; //中间如果有空行，则退出
                }
                int cellNum = row.getLastCellNum();                     //每行的最后一个单元格位置
                if(i!=0){//记录数据的行号
                    map=new HashMap<String, Object>();
                    map.put("lineNumber", i+1);
                }
                for (int j = startcol; j < cellNum; j++) {               //列循环开始
                    HSSFCell cell2 = row.getCell(j);
                    //HSSFCell cell = row.getCell(Short.parseShort(j + ""));
                    //String cellValue = getCellValue(cell, format);
                    String cellValue = getCellValue(cell2);
                    //如果当前行的第一列为空,则跳过该行
                    /*if(j==0 && ("".equals(cellValue)||null==cellValue)){
                    	break;
                    }*/
                    if(i==0){
                        Header.put(j+"", cellValue);
                    }else{
                        map.put((String)Header.get(j+""), cellValue);
                    }
                }
                //每一行数据的过滤,如果一行中所有列都为null,则不生成该行数据,
                if(null!=map){
                    Collection<Object> values = map.values();
                    Set<String> keySet = map.keySet();
                    long count2 = keySet.stream().filter(o -> !o.equals("lineNumber")).count();//总条数
                    long count = values.stream().filter(o -> null==o).count();//为空的条数
                    if(count<count2){
                        list.add(map);
                    }
                }
            }
            workbook.close();
        } catch (Exception e) {
            throw e;
        }
        return list;
    }






    /**
     * 根据excel单元格类型获取excel单元格值
     * @param cell
     * @return
     */
    private static String getCellValue(HSSFCell cell) {
        String cellvalue = null;
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case NUMERIC: {
                    short format = cell.getCellStyle().getDataFormat();
                    if(format == 14 || format == 31 || format == 57 || format == 58){   //excel中的时间格式
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        double value = cell.getNumericCellValue();
                        Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                        cellvalue = sdf.format(date);
                    }
                    // 判断当前的cell是否为Date
                    else if (HSSFDateUtil.isCellDateFormatted(cell)) {  //先注释日期类型的转换，在实际测试中发现HSSFDateUtil.isCellDateFormatted(cell)只识别2014/02/02这种格式。
                        // 如果是Date类型则，取得该Cell的Date值           // 对2014-02-02格式识别不出是日期格式
                        Date date = cell.getDateCellValue();
                        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue= formater.format(date);
                    } else { // 如果是纯数字
                        // 取得当前Cell的数值
                        cellvalue = NumberToTextConverter.toText(cell.getNumericCellValue());

                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getStringCellValue().replaceAll("'", "''");
                    break;
                case  BLANK:
                    cellvalue = null;
                    break;
                // 默认的Cell值
                default:{
                    cellvalue = "";
                }
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }
}
