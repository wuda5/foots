package com.cdqckj.gmis.statistics.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/11/18 11:13
 * @remark: 面板的时间的统计的类型
 */
@Data
@ApiModel(value = "PanelTimeGroupVo", description = "面板的时间的统计的类型")
public class PanelTimeGroupVo<T> {

    @ApiModelProperty(value = "查询的时间统计")
    private T searchDate;

    @ApiModelProperty(value = "本月的统计数据")
    private T nowDate;

    @ApiModelProperty(value = "上月的统计数据")
    private T lastMonthDate;

    @ApiModelProperty(value = "去年同期的统计数据")
    private T lastYearDate;

    @ApiModelProperty(value = "类型选择")
    private List<StsTypeVo> typeList;

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/14 11:20
    * @remark 添加一个统计类型
    */
    public void addStsType(StsTypeVo type){
        if (typeList == null){
            typeList = new ArrayList<>();
        }
        for (StsTypeVo vo: typeList){
            if (vo.getTypeId().equals(type.getTypeId())){
                return;
            }
        }
        typeList.add(type);
    }
}
