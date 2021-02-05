package com.cdqckj.gmis.base.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExcelCascadeSelectorDTO  implements Serializable {

    private static final long serialVersionUID = ExcelCascadeSelectorDTO.class.hashCode();
    /**
     * 数据源sheet名称
     */
    @ApiModelProperty(value = "数据源sheet名称")
    private String sheetName ;

    /**
     * 下拉单元格行号，从0开始
     */
    @ApiModelProperty(value = "下拉单元格行号，从1开始，默认值1")
    private int firstRow;


    /**
     * 下拉单元格结束行号
     */
    @ApiModelProperty(value = "下拉单元格结束行号，默认值10000")
    private int lastRow;
    /**
     * 级联列
     */
    @ApiModelProperty(value = "级联列")
    private char column;

    /**
     * 级联层级
     */
    @ApiModelProperty(value = "级联层级,默认0，表示根层级")
    private int index;

    /**
     * 一组级联，用相同标志，区分和其他不同的级联组
     */
    @ApiModelProperty(value = "级联组标记")
    private String cascadeMark;

    private Boolean preSub;

    /**
     * 动态生成的下拉内容
     */
    @ApiModelProperty(value = "下拉内容")
    private TreeSet<String> zeroCascadeSelectorSet = new TreeSet<>();;

    /**
     * 动态生成的下拉内容
     */
    @ApiModelProperty(value = "下拉内容")
    private  Map<String, Object> cascadeSelectorMap = new HashMap<>();

    public ExcelCascadeSelectorDTO(String sheetName, char column, int index, TreeSet<String> zeroCascadeSelectorSet, String cascadeMark) {
        this.sheetName = sheetName;
        this.firstRow = 1;
        this.lastRow = 10000;
        this.column = column;
        this.index = index;
        this.zeroCascadeSelectorSet = zeroCascadeSelectorSet;
        this.cascadeMark = cascadeMark;
    }

    public ExcelCascadeSelectorDTO(String sheetName, char column, int index, Map<String, Object> cascadeSelectorMap, String cascadeMark) {
        this.sheetName = sheetName;
        this.firstRow = 1;
        this.lastRow = 10000;
        this.column = column;
        this.index = index;
        this.cascadeSelectorMap = cascadeSelectorMap;
        this.cascadeMark = cascadeMark;
    }
}
