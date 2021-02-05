package com.cdqckj.gmis.statistics.domain.log;

import com.cdqckj.gmis.statistics.domain.log.util.EntityFieldUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/10/23 17:26
 * @remark: 请输入类说明
 */
@Data
@ApiModel("一行的数据")
public class FormatRowData<T> {

    @ApiModelProperty(value = "之前的值")
    private T beforeRowValue;

    @ApiModelProperty(value = "之后的的值")
    private T afterRowValue;

    @ApiModelProperty(value = "所有的字段都")
    private List<FormatColData> colFieldList = new ArrayList<>();

    @ApiModelProperty(value = "修改的列")
    private List<String> changeColNameList = new ArrayList<>();

    public void setColFieldList(List<FormatColData> colFieldList) {
        this.colFieldList = colFieldList;
        for (FormatColData formatColData: colFieldList){
            if (formatColData.getChange() == true){
                changeColNameList.add(formatColData.getFieldName());
            }
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/10/27 9:18
     * @remark 这一个列的数据是否发生改变
     */
    public Boolean colChange(String colName){
        String formatName = EntityFieldUtil.fieldToCamelCase(colName);
        if (changeColNameList.contains(formatName)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/10/27 9:20
     * @remark 多个col改变
     */
    public Boolean anyColChange(String... colNAmeList){
        for (String colName: colNAmeList){
            if (colChange(colName)){
                return true;
            }
        }
        return false;
    }
}
