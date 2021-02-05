package com.cdqckj.gmis.base.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelCascadeUtils {


    /**
     * 级联导出
     */
    public static void exportCascadeExcel(List<?> list, List<ExcelCascadeSelectorDTO> excelCascadeSelectors, Class<?> pojoClass, String fileName,
                                          HttpServletResponse response, int targetSheetIndex) {
        defaultCascadeExport(list, excelCascadeSelectors, pojoClass, fileName, response, new ExportParams(), targetSheetIndex);
    }

    private static void defaultCascadeExport(List<?> list, List<ExcelCascadeSelectorDTO> excelCascadeSelectors,  Class<?> pojoClass, String fileName, HttpServletResponse response,
                                             ExportParams exportParams, int targetSheetIndex) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        workbook = generateCascadeSelectors(workbook, excelCascadeSelectors, targetSheetIndex);
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("@"));
        Sheet sheetAt = workbook.getSheetAt(0);
        Row fristRow = sheetAt.getRow(0);
        short lastCellNum = fristRow.getLastCellNum();
        for(int i = 0; i<lastCellNum; i++){
            sheetAt.setDefaultColumnStyle(i,cellStyle);
        }
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * 级联导出
     */
    public static void exportCascadeExcel2(List<?> list, List<ExcelCascadeSelectorDTO> excelCascadeSelectors, Class<?> pojoClass, String fileName,
                                          HttpServletResponse response, int targetSheetIndex) {
        defaultCascadeExport2(list, excelCascadeSelectors, pojoClass, fileName, response, new ExportParams(), targetSheetIndex);
    }

    private static void defaultCascadeExport2(List<?> list, List<ExcelCascadeSelectorDTO> excelCascadeSelectors,  Class<?> pojoClass, String fileName, HttpServletResponse response,
                                             ExportParams exportParams, int targetSheetIndex) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        workbook = generateCascadeSelectors(workbook, excelCascadeSelectors, targetSheetIndex);
        CellStyle cellStyle1 = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        cellStyle1.setDataFormat(dataFormat.getFormat("@"));
        Sheet sheetAt = workbook.getSheetAt(0);
        sheetAt.setDefaultColumnStyle(6,cellStyle1);
        sheetAt.setDefaultColumnStyle(7,cellStyle1);
        sheetAt.setDefaultColumnStyle(4,cellStyle1);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * 生成级联下拉框
     * @Authhor songyz
     * @param workbook
     * @param excelCascadeSelectors
     * @return
     */
    public static Workbook generateCascadeSelectors(Workbook workbook, List<ExcelCascadeSelectorDTO> excelCascadeSelectors, int targetSheetIndex){
        if (CollectionUtils.isEmpty(excelCascadeSelectors)) {
            return workbook;
        }
        int sheetHidden = targetSheetIndex+1;
        Sheet sheet = workbook.getSheetAt(targetSheetIndex);
        Map<String, List<ExcelCascadeSelectorDTO>> groupBy = excelCascadeSelectors.stream().collect(Collectors.groupingBy(ExcelCascadeSelectorDTO::getCascadeMark));
        for (Map.Entry<String, List<ExcelCascadeSelectorDTO>> groupByEntry : groupBy.entrySet()) {
            for (ExcelCascadeSelectorDTO cascadeSelector : groupByEntry.getValue()) {


                String hiddenSheetName = cascadeSelector.getSheetName();
                Sheet hiddenSheet = workbook.createSheet(hiddenSheetName);
                int index = cascadeSelector.getIndex();
                char column = cascadeSelector.getColumn();
                TreeSet<String> zeroCascadeSelectorSet = cascadeSelector.getZeroCascadeSelectorSet();
                Map<String, Object> cascadeSelectorMap = cascadeSelector.getCascadeSelectorMap();

                if (index == 0) {
                    int rowIndex = 0;
                    for (Iterator iter = zeroCascadeSelectorSet.iterator(); iter.hasNext(); ) {
                        String value = (String) iter.next();
                        if(cascadeSelector.getPreSub()!=null && cascadeSelector.getPreSub()){
                            value=value.substring(5,value.length()-0);
                        }
                        Row row = hiddenSheet.createRow(rowIndex++);
                        Cell cell = row.createCell(0);
                        cell.setCellValue(value);
                    }
                    String formula = hiddenSheetName + "!$A$1:$A$" + zeroCascadeSelectorSet.size();
                    createName((HSSFWorkbook) workbook, hiddenSheetName, formula);
                    HSSFDataValidation validation = getDataValidation(formula, cascadeSelector.getFirstRow(), cascadeSelector.getLastRow(),
                            cascadeSelector.getColumn() - 'A', cascadeSelector.getColumn() - 'A');
                    sheet.addValidationData(validation);
                } else {
                    int rowIndex = 0;
                    for (Map.Entry<String, Object> entry : cascadeSelectorMap.entrySet()) {
                        int start = rowIndex + 1;
                        ArrayList<String> valueArrayList = (ArrayList<String>) entry.getValue();
                        String nameName = entry.getKey();
                        //迭代器遍历
                        Iterator<String> iterator = valueArrayList.iterator();
                        while (iterator.hasNext()) {
                            Row row = hiddenSheet.createRow(rowIndex++);
                            Cell keyCell = row.createCell(0);
                            keyCell.setCellValue(nameName);
                            Cell valueCell = row.createCell(1);
                            valueCell.setCellValue(iterator.next());

                        }
                        //名称管理
                        int end = rowIndex;
                        if (start - 1 != end) {
                            String refersToFormula = hiddenSheetName + "!$B$" + start + ":$B$" + end;
                            createName((HSSFWorkbook) workbook, nameName, refersToFormula);
                        }

                    }
                    //有效性
                    for (int rowNum = cascadeSelector.getFirstRow(); rowNum < cascadeSelector.getLastRow(); rowNum++) {

                        String formulaConstraint = null;
                        if (index == 1) {
                            char keyColumn = (char) (column - 1);
                            int keyNum = rowNum + 1;
                            formulaConstraint = "INDIRECT($" + String.valueOf(keyColumn) + "$" + keyNum + ")";
                        } else {
                            Set<String> characterSet = new TreeSet<>();
                            int j = 1;
                            for (int i = index; i > 0; i--) {
                                char keyColumn = (char) (column - j);
                                characterSet.add(String.valueOf(keyColumn));
                                j++;
                            }
                            Iterator<String> characterSetIterator = characterSet.iterator();
                            String str = "";
                            int keyNum = rowNum + 1;
                            while (characterSetIterator.hasNext()) {
                                str = str.concat("$").concat(characterSetIterator.next()).concat("$").concat(String.valueOf(keyNum)).concat(",");
                            }
                            str = str.substring(0, str.length() - 1);
                            formulaConstraint = "INDIRECT(CONCATENATE(" + str + "))";
                        }
                        HSSFDataValidation validation = getDataValidation(formulaConstraint, rowNum, rowNum,
                                cascadeSelector.getColumn() - 'A', cascadeSelector.getColumn() - 'A');
                        sheet.addValidationData(validation);
                    }

                }
                //将sheet设置为隐藏
                workbook.setSheetHidden(sheetHidden, true);
                sheetHidden++;
            }
        }

        return workbook;
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
     * 创建名称
     * @param wb
     * @param name
     * @param expression
     * @return
     */

    public static HSSFName createName(HSSFWorkbook wb, String name, String expression){
        HSSFName refer = wb.createName();
        refer.setRefersToFormula(expression);
        name = name.replaceAll("/","\\u002f");
        refer.setNameName(name);
        return refer;
    }

    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }

    /**
     * 获取数据有效性对象
      */
    public static HSSFDataValidation getDataValidation(String formulaConstraint, int firstRow, int endRow, int firstCol, int endCol){

        //加载下拉列表内容
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(formulaConstraint);
        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList((short)firstRow, (short)endRow,(short)firstCol,(short)endCol);
        // 数据有效性对象
        HSSFDataValidation validation = new HSSFDataValidation(regions, constraint);
        return validation;
    }


}
