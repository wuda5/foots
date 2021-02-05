package com.cdqckj.gmis.base.request;

import cn.afterturn.easypoi.excel.export.styler.ExcelExportStylerDefaultImpl;
import org.apache.poi.ss.usermodel.*;

public class ExcelExportStylerSelfImpl extends ExcelExportStylerDefaultImpl {
    public ExcelExportStylerSelfImpl(Workbook workbook) {
        super(workbook);
    }


    public void setCellStyleAt() {
        CellStyle titleStyle = this.workbook.getCellStyleAt(1);
        Font font = this.workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short)12);
        titleStyle.setFont(font);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    }

    @Override
    public CellStyle getTitleStyle(short color) {
        setCellStyleAt();
        CellStyle titleStyle = this.workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setWrapText(true);
        return titleStyle;
    }
}
