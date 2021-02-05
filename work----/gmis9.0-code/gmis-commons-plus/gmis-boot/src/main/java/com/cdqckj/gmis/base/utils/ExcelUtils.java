package com.cdqckj.gmis.base.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
    public static void exportExcel(List<?> list, List<ExcelSelectortDTO> selectors, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        defaultExport(list, selectors, pojoClass, fileName, response, new ExportParams());
    }

    private static void defaultExport(List<?> list, List<ExcelSelectortDTO> selectors,  Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        workbook = generateSelectors(workbook, selectors);
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("@"));
        Sheet sheetAt = workbook.getSheetAt(0);
        sheetAt.setDefaultColumnStyle(1,cellStyle);
        sheetAt.setDefaultColumnStyle(2,cellStyle);
        sheetAt.setDefaultColumnStyle(3,cellStyle);
        sheetAt.setDefaultColumnStyle(4,cellStyle);
        sheetAt.setDefaultColumnStyle(5,cellStyle);
        sheetAt.setDefaultColumnStyle(6,cellStyle);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    public static Workbook generateSelectors(Workbook workbook, List<ExcelSelectortDTO> dataList){
        if (CollectionUtils.isEmpty(dataList)) {
            return workbook;
        }
        Sheet sheet = null;
        CellRangeAddressList cellRangeAddressList;
        DVConstraint dvConstraint;
        HSSFDataValidation dataValidation;
        int sheetHidden = 0;
        for (ExcelSelectortDTO data : dataList) {
            sheet = workbook.getSheetAt(data.getSheetIndex());
            if(data.getDatas().length <= 255){
                // 只对(x,x)单元格有效
                cellRangeAddressList = new CellRangeAddressList(data.getFirstRow(), data.getLastRow(), data.getFirstCol(), data.getLastCol());
                // 生成下拉框内容
                dvConstraint = DVConstraint.createExplicitListConstraint(data.getDatas());
                dataValidation = new HSSFDataValidation(cellRangeAddressList, dvConstraint);
                // 对sheet页生效
                sheet.addValidationData(dataValidation);
            }
            else if(data.getDatas().length > 255){
                sheetHidden ++;
                biggerCharacterSelector(workbook, sheet, data, sheetHidden);
            }
        }
        return workbook;
    }

    private String findRange(List<Map<String,Object>> list,String excelColumn,String locationId){
        int firstRow = 1;
        int lastRow = 1;
        int rownum = 1;
        boolean finded = false;
        for(Map<String,Object> map : list){
            Integer parentId = (Integer)map.get("parentId");
            if(parentId.toString().equals(locationId)){
                if(!finded){
                    firstRow = rownum;
                    lastRow = rownum;
                    finded = true;
                }else{
                    lastRow++;
                }
            }else if(finded){
                break;
            }
            rownum ++;
        }
        return "$"+excelColumn+"$"+firstRow+":"+"$"+excelColumn+"$"+lastRow;
    }

    /**
     * 根据数据值确定单元格位置（比如：0-A, 27-AB）
     *
     * @param index
     * @return
     */
    public static String getColumnName(int index) {
        StringBuilder s = new StringBuilder();
        while (index >= 26) {
            s.insert(0, (char) ('A' + index % 26));
            index = index / 26 - 1;
        }
        s.insert(0, (char) ('A' + index));
        return s.toString();
    }

    private static Name createName(Workbook workbook, String nameName, String formula) {
        Name name = workbook.createName();
        name.setNameName(nameName);
        name.setRefersToFormula(formula);
        return name;
    }

    /**
     * 不可数字开头

     * @param name
     * @return
     */
    static String formatNameName(String name) {
        name = name.replaceAll(" ", "").replaceAll("-", "_").replaceAll(":", ".");
        if (Character.isDigit(name.charAt(0))) {
            name = "_" + name;
        }

        return name;
    }

    /**
     * 单元格的字符数大于255
     * @param workbook
     * @param sheet
     * @param data
     */
    private static void biggerCharacterSelector(Workbook workbook, Sheet sheet, ExcelSelectortDTO data, int sheetHidden) {
        String hiddenName = "hidden_" + (int)((Math.random()*9+1)*100);
        HSSFSheet hidden = (HSSFSheet) workbook.createSheet(hiddenName);
        HSSFCell cell = null;
        for (int i = 0, length = data.getDatas().length; i < length; i++)
        {
            String name = data.getDatas()[i];
            HSSFRow row = hidden.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(name);
        }
        Name namedCell = workbook.createName();
        namedCell.setNameName(hiddenName);
        namedCell.setRefersToFormula(hiddenName+"!$A$1:$A$" + data.getDatas().length);
        //加载数据,将名称为hidden的sheet中的数据转换为List形式
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(hiddenName);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(data.getFirstRow(), data.getLastRow(), data.getFirstCol(), data.getLastCol());
        // 数据有效性对象
        HSSFDataValidation dataValidationList = new HSSFDataValidation(regions, constraint);
        //将sheet设置为隐藏
        workbook.setSheetHidden(sheetHidden, true);
        if (null != dataValidationList) {
            sheet.addValidationData(dataValidationList);
        }
    }

    // 执行导出的方法
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        OutputStream out = null;
        BufferedOutputStream buf = null;
        try {
            response.reset(); //重置响应对象
            out = response.getOutputStream();
            buf = new BufferedOutputStream(out);

            String excelName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
            response.setHeader("content-Type", "application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + excelName);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            workbook.write(buf);
            buf.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (null != buf) {
                    buf.close();
                    buf = null;
                }
                if (null != out) {
                    out.close();
                    out = null;
                }
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
