package com.cdqckj.gmis.common.domain.excell;

import cn.hutool.core.util.ReflectUtil;
import com.cdqckj.gmis.common.domain.excell.rule.UniqueColRule;
import com.cdqckj.gmis.common.domain.excell.rule.ValidColRule;
import com.cdqckj.gmis.common.domain.excell.rule.ValidRule;
import com.cdqckj.gmis.common.domain.excell.rule.ValidRuleDefault;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/9/30 8:50
 * @remark: 导入数据的验证
 */
@Log4j2
public class ExcelImportValid{

    @ApiModelProperty(value = "要验证的数据的行")
    private List<Row> rowList;

    @ApiModelProperty(value = "行的验证规则")
    private List<ValidRule> validRuleList = new ArrayList<>();

    @ApiModelProperty(value = "列的验证规则")
    private List<ValidColRule> validColRuleList = new ArrayList<>();

    @ApiModelProperty(value = "验证的结果")
    private List<List<ValidResult>> validResult;

    @ApiModelProperty(value = "要验证的excel")
    private HSSFWorkbook workbook;

    public ExcelImportValid(HSSFWorkbook workbook, int startRow) {
        this.workbook = workbook;
        this.rowList = initRowList(workbook, startRow);
    }

    /**
     * @auth lijianguo
     * @date 2020/10/12 16:34
     * @remark 初始化行的数据
     */
    private List<Row> initRowList(HSSFWorkbook workbook, int startRow) {
        Sheet sheetAt = workbook.getSheetAt(0);
        List<Row> rowList = new ArrayList<>(sheetAt.getPhysicalNumberOfRows());
        /*for (int i = startRow; i < sheetAt.getPhysicalNumberOfRows(); i++) {
            Row row = sheetAt.getRow(i);
            if (row != null) {
                rowList.add(row);
            }
        }*/
        for(Row row:sheetAt){
            if (row.getRowNum()>=startRow && row != null) {
                rowList.add(row);
            }
        }
        return rowList;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/27 13:24
     * @remark 类型空第几列验证
     */
    public void addValidBaseRule(ValidTypeEnum typeEnum, String validDesc, String emptyDesc, Integer colStart){
        addValidBaseRule(typeEnum, validDesc, emptyDesc, colStart, 1);
    }

    /**
     * @auth lijianguo
     * @date 2020/10/27 13:24
     * @remark 类型 空 开始列 验证的列
     */
    public void addValidBaseRule(ValidTypeEnum typeEnum, String validDesc, String emptyDesc, Integer colStart, Integer colSum){
        if (typeEnum == null){
            typeEnum = ValidTypeEnum.NOT_VALID;
        }
        ValidRule validRule = new ValidRuleDefault(typeEnum.getValue(), validDesc, emptyDesc, colStart, colSum);
        this.addValidBaseRule(validRule);
    }

    public void addValidBaseRule(ValidRule newRule){
        if (newRule.validColStart() == null){
            throw new RuntimeException("起始列编号不能为空");
        }
        if (newRule.validColSum() == null){
            throw new RuntimeException("总列数不能为空");
        }
        validRuleList.add(newRule);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/12 11:00
    * @remark 这些列必须满足唯一性
    */
    public void colUniqueRule(Integer col,String failDescribe){
        UniqueColRule colRule = new UniqueColRule(col, 0, rowList.size(), failDescribe);
        addColRule(colRule);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/12 10:55
    * @remark 添加列的验证规则
    */
    public void addColRule(ValidColRule colRule){

        if (colRule == null){
            throw new RuntimeException("列的验证规则不能为空");
        }
        if (colRule.validCol() == null){
            throw new RuntimeException("列不能为空");
        }
        if (colRule.validStartRow() == null){
            throw new RuntimeException("起始行不能为空");
        }
        if (colRule.validEndRow() == null){
            throw new RuntimeException("结束行不能为空");
        }
        validColRuleList.add(colRule);
    }

    /**
     * @auth lijianguo
     * @date 2020/9/30 12:23
     * @remark 验证成功 返回true 失败返回false
     */
    public boolean validAllSuc(){

        List<List<ValidResult>> resultList = validExcelResult();
        for (int i = 0; i < resultList.size(); i++){
            List<ValidResult> dataRow = resultList.get(i);
            for (int j = 0; j < dataRow.size(); j++){
                ValidResult result = dataRow.get(j);
                if (result.getStatus() == false){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @auth lijianguo
     * @date 2020/9/30 13:17
     * @remark 设置cell的错误的样式
     */
    public void setCellWrongStyle(){

        List<List<ValidResult>> resultList = validExcelResult();
        for (int i = 0; i < resultList.size(); i++){
            List<ValidResult> validResultList = resultList.get(i);
            Row row = rowList.get(i);
            for (ValidResult valid: validResultList){
                // 验证成功就不需要设置样式
                if (valid.getStatus() == null || valid.getStatus() == true){
                    continue;
                }
                List<Cell> validCellList = new ArrayList<>();
                for (int j = 0; j < valid.getColSum(); j++){

                    int index = valid.getCol() + j;
                    Cell cell = row.getCell(index);
                    if (cell == null){
                        cell = row.createCell(index);
                    }
                    validCellList.add(cell);
                }
                setValidWrongCellStyle(validCellList, valid);
            }
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/10/15 16:31
     * @remark 色值错误的cell的样式
     */
    private void setValidWrongCellStyle(List<Cell> cellList, ValidResult valid){

        Sheet sheet = workbook.getSheetAt(0);

        HSSFCellStyle style = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        style.setDataFormat(dataFormat.getFormat("@"));
        HSSFFont redFont = workbook.createFont();
        redFont.setColor(Font.COLOR_RED);
        style.setFont(redFont);

        Cell cell = cellList.get(cellList.size()-1);
        cell.setCellStyle(style);
        HSSFPatriarch p = (HSSFPatriarch) sheet.createDrawingPatriarch();
        HSSFComment comment = (HSSFComment) cell.getCellComment();
        if (comment == null){
            comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
            cell.setCellComment(comment);
        }
        comment.setString(new HSSFRichTextString(valid.getInvalidDesc()));
    }

    /**
     * @auth lijianguo
     * @date 2020/9/30 9:42
     * @remark 验证Excel的表格 --- 这里会清除样式
     */
    public List<List<ValidResult>> validExcelResult(){

        if (validResult == null) {
            cleanExcelStyle();
            List<List<ValidResult>> resultList = new ArrayList<>(rowList.size());
            // 每一行的验证
            ValidRowData(resultList);
            // 列的验证--列的验证只针对单独的一列验证
            ValidColData(resultList);
            validResult = resultList;
        }
        return validResult;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/14 17:30
    * @remark 对行的验证规则进行验证---行的验证规则
    */
    private void ValidRowData(List<List<ValidResult>> resultList) {
        for (int i = 0; i < rowList.size(); i++) {
            List<ValidResult> rowResultList = new ArrayList<>(validRuleList.size());
            for (int j = 0; j < validRuleList.size(); j++) {
                ValidRule validRule = validRuleList.get(j);
                int cellIndex = validRule.validColStart();
                List<Cell> ruleCellList = new ArrayList<>(validRule.validColSum());
                for (int k = 0; k < validRule.validColSum(); k++){
                    Cell cell = rowList.get(i).getCell(cellIndex);
                    ruleCellList.add(cell);
                    cellIndex++;
                }
                rowResultList.add(processOneCell(ruleCellList, validRule, i, validRule.validColStart(), validRule.validColSum()));
            }
            resultList.add(rowResultList);
        }
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/14 17:30
     * @remark 对列的数据进行验证--列的验证规则
     */
    private void ValidColData(List<List<ValidResult>> resultList) {
        for (int i = 0; i < validColRuleList.size(); i++){
            ValidColRule colRule = validColRuleList.get(i);
            List<String> colDataList = new ArrayList<>(colRule.validEndRow() - colRule.validStartRow());
            for (int row = 0; row < rowList.size(); row++) {
                String cellStr = getCellString(row, colRule.validCol());
                colDataList.add(cellStr);
            }
            List<ValidResult> colResult = colRule.validColData(colDataList);
            if (colResult.size() != colDataList.size()){
                throw new RuntimeException("验证的行数不相等");
            }
            for (int row = 0; row < rowList.size(); row++){
                List<ValidResult> rowResultList = resultList.get(row);
                ValidResult theResult = colResult.get(row);
                rowResultList.add(theResult);
            }
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/10/27 12:53
     * @remark 清除表格的样式--所以只要验证都会清除样式
     */
    private void cleanExcelStyle(){

        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont redFont = workbook.createFont();
        redFont.setColor(Font.COLOR_NORMAL);
        style.setFont(redFont);
        for (int i = 0; i < rowList.size(); i++){
            Row row = rowList.get(i);
            int columnNum = row.getLastCellNum();
            for (int j = 0; j < columnNum; j++){
                Cell cell = row.getCell(j);
                if(cell == null){
                    cell = row.createCell(j);
                }
                if (cell.getCellType() != null){
                    cell.setCellStyle(style);
                    cell.removeCellComment();
                }
            }
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/9/30 9:55
     * @remark 每一个行的验证
     */
    private ValidResult processOneCell(List<Cell> cellList, ValidRule validRule, int i, int j, int colSum){

        ValidResult validResult = new ValidResult();
        validResult.setRow(i);
        validResult.setCol(j);
        validResult.setColSum(colSum);
        List<String> valueList = getCellListValue(cellList);
        validResult.setColDataList(valueList);
        validResult.setStatus(true);
        // 这里是要求这个格子所有的不能为空的验证
        if (validRule.validEmptyFailDesc() != null){
            for (String value: valueList){
                if (StringUtils.isAnyBlank(value)){
                    validResult.setStatus(false);
                    validResult.setInvalidDesc(validRule.validEmptyFailDesc());
                }
            }
        }
        // 这里处理各种验证规则--要空的验证通过才做数据的验证
        if (StringUtils.isNotBlank(validRule.validFailDesc()) && validResult.getStatus() == true) {
            validRule.validProcess(validResult);
        }
        return validResult;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/12 17:03
     * @remark getCellListValue
     */
    private List<String> getCellListValue(List<Cell> cellList){

        List<String> dataList = new ArrayList<>(cellList.size());
        for (Cell cell: cellList){
            dataList.add(getCellValue(cell));
        }
        return dataList;
    }

    /**
     * @auth lijianguo
     * @date 2020/9/30 9:53
     * @remark 获取cell的String的值
     */
    private String getCellValue(Cell cell){
        String cellStr ;
        if (cell == null){
            return null;
        }else {
            CellType cellType = cell.getCellType();
            if(cellType == CellType.NUMERIC){
                Double doubleValue = cell.getNumericCellValue();
                if (doubleValue == null){
                    cellStr = "";
                }else {
                    DecimalFormat df = new DecimalFormat("#");
                    df.setMaximumFractionDigits(15);
                    cellStr = df.format(doubleValue);
                }
            }else if (cellType == CellType.STRING){

                cellStr = cell.getStringCellValue();
            }else if (cellType == CellType.BLANK){

                cellStr = "";
            } else {
                throw new RuntimeException("该格式不支持");
            }
            if (cellStr != null){
                cellStr = cellStr.trim();
            }
            return cellStr;
        }
    }

     /**
     * @Author: lijiangguo
     * @Date: 2020/12/11 13:53
     * @remark 获取cell的data返回一个二维数组 type 1全部 2成功的data 3失败的data
     */
     public List<RowDataInfo> getCellDataToArray(DataTypeEnum type){

         List<List<ValidResult>> validList = validExcelResult();
         List<RowDataInfo> result = new ArrayList<>();
         Integer maxNum = getTotalColNum();
         for (int i = 0; i < validList.size(); i++){
             Boolean checkSuc = checkRowData(validList.get(i), type);
             if (checkSuc == true) {
                 RowDataInfo rowDataInfo = new RowDataInfo(i);
                 for (int j = 0; j < maxNum; j++) {
                     String cellValue = getCellString(i, j);
                     rowDataInfo.addColValue(cellValue);
                 }
                 result.add(rowDataInfo);
             }
         }
         return result;
     }

     /**
     * @Author: lijiangguo
     * @Date: 2020/12/11 16:36
     * @remark 转换为class
     */
    public List getCellDataToArray(DataTypeEnum type, Class cla){

        List<RowDataInfo> result = getCellDataToArray(type);
        List obResult = new ArrayList<>();
        if (result.size() == 0){
            return obResult;
        }
        for (int i = 0; i < result.size(); i++) {
            Object object = ReflectUtil.newInstanceIfPossible(cla);
            Field[] fields = object.getClass().getDeclaredFields();
            RowDataInfo rowData = result.get(i);
            for (int j = 0; j < rowData.getColDataList().size(); j++) {
                String value = rowData.getColDataList().get(j);
                if (j < fields.length){
                    Field f = fields[j];
                    if (StringUtils.isNotBlank(value)) {
                        // 如果这里的字段为时间就需要转换
                        Object covertObject = ExcelDateUtil.covertToDate(value, f.getType());
                        ReflectUtil.setFieldValue(object, f, covertObject);
                    }
                }
            }
            obResult.add(object);
        }
        if (obResult.size() <= 0 || !(obResult.get(0) instanceof RowFailDescribe)){
            return obResult;
        }
        return setRowFailDescribe(obResult, result);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/16 11:44
    * @remark 设置错误的描述
    */
    private List setRowFailDescribe(List obResult, List<RowDataInfo> rowDataInfoList) {

        List<List<ValidResult>> validList = validExcelResult();
        for (int i = 0; i < obResult.size(); i++){
            RowFailDescribe o = (RowFailDescribe) obResult.get(i);
            RowDataInfo rowDataInfo = rowDataInfoList.get(i);
            List<ValidResult> rowList = validList.get(rowDataInfo.getRow());
            List<FailNumInfo> failInfoList = new ArrayList<>();
            for (int j = 0; j < rowList.size(); j++){
                ValidResult validResult = rowList.get(j);
                if (validResult.getStatus() == false){
                    FailNumInfo rowFailNumInfo = new FailNumInfo(j, validResult.getInvalidDesc());
                    failInfoList.add(rowFailNumInfo);
                }
            }
            o.setMsg(failInfoList);
        }
        return obResult;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/11 14:45
     * @remark 这一列的数据需要添加到返回的字符串 type 1全部 2成功的data 3失败的data
     */
     private boolean checkRowData(List<ValidResult> rowDataList, DataTypeEnum type){
         if (DataTypeEnum.ALL == type){
             return true;
         }
         // 如果对着一行所有的验证都成功才为成功
         Boolean status = true;
         for (ValidResult col: rowDataList){
             if (col.getStatus() == false){
                 status = false;
                 break;
             }
         }
         if (DataTypeEnum.SUC == type){
             return status;
         }else {
             return !status;
         }
     }

     /**
     * @Author: lijiangguo
     * @Date: 2020/12/11 14:04
     * @remark 获取列的个数
     */
     private Integer getTotalColNum() {

         Integer maxNum = 0;
         for (int i = 0; i < rowList.size(); i++) {
             Row row = rowList.get(i);
             int columnNum = row.getLastCellNum();
             maxNum = maxNum > columnNum ? maxNum: columnNum;

         }
         return maxNum;
     }

     /**
     * @Author: lijiangguo
     * @Date: 2020/12/11 14:19
     * @remark 获取地 i，j,的string
     */
     private String getCellString(Integer i, Integer j){

         if (i > rowList.size()){
             return "";
         }
         Row row = rowList.get(i);
         Cell cell = row.getCell(j);
         if (cell == null){
             return "";
         }
         return getCellValue(cell);
     }

     /**
     * @Author: lijiangguo
     * @Date: 2020/12/11 15:16
     * @remark 清空excel的 没一列的数据 type 1全部 2成功的data 3失败的data
     */
     public void clearRow(DataTypeEnum type){
         List<List<ValidResult>> validList = validExcelResult();
         Sheet sheet = workbook.getSheetAt(0);
         int lastRowNum = sheet.getLastRowNum();
         for (int i = 0; i < validList.size(); i++){
             Boolean checkSuc = checkRowData(validList.get(i), type);
             // 删除这个类型的row
             if (checkSuc == true){
                 Row row = rowList.get(i);
                 row.getRowNum();
                 sheet.removeRow(row);
                 if(row.getRowNum() + 1 <= lastRowNum) {
                     sheet.shiftRows(row.getRowNum() + 1, lastRowNum, -1);
                 }else{
                     sheet.shiftRows(lastRowNum, lastRowNum, -1);
                 }
             }
         }
     }

     /**
     * @Author: lijiangguo
     * @Date: 2020/12/14 11:01
     * @remark 上传文件到
     */
     public String uploadFile(UploadFileInfo file, GmisUploadFile gmisUploadFile){

         ByteArrayOutputStream bos = new ByteArrayOutputStream();
         try {
             workbook.write(bos);
         } catch (IOException e) {
             e.printStackTrace();
         }
         byte[] byteArray = bos.toByteArray();
         InputStream is = new ByteArrayInputStream(byteArray);
         MultipartFile multipartFile = null;
         try {
             multipartFile = new MockMultipartFile(file.getFileName(), file.getOriName(), file.getContentType(), is);
         } catch (IOException e) {
             e.printStackTrace();
         }
         String url = gmisUploadFile.uploadGetUrl(multipartFile, file.getBizType());
         return url;
     }
}
