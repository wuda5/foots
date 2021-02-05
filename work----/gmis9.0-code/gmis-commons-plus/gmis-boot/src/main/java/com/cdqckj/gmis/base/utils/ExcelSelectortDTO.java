package com.cdqckj.gmis.base.utils;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelSelectortDTO {

    /**
     * 第几个sheet，从0开始
     */
    @ApiModelProperty(value = "第几个sheet，从0开始，默认值0")
    private int sheetIndex = 0;

    /**
     * 下拉单元格行号，从0开始
     */
    @ApiModelProperty(value = "下拉单元格行号，从0开始，默认值1")
    private int firstRow = 1;

    /**
     * 下拉单元格结束行号
     */
    @ApiModelProperty(value = "下拉单元格结束行号，默认值10000")
    private int lastRow = 10000;

    /**
     * 下拉单元格列号，从0开始
     */
    @ApiModelProperty(value = "下拉单元格列号，从0开始")
    private int firstCol;

    /**
     * 下拉单元格列(结束)
     */
    @ApiModelProperty(value = "下拉单元格列(结束)，尽量与firstCol一致")
    private int lastCol;

    /**
     * 动态生成的下拉内容，easypoi使用的是字符数组
     */
    @ApiModelProperty(value = "下拉内容：字符数组")
    private String[] datas;

    public ExcelSelectortDTO(int firstCol, int lastCol, String[] datas){
        this.firstCol = firstCol;
        this.lastCol = lastCol;
        this.datas = datas;
    }
}
