package com.cdqckj.gmis.statistics.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/12/03 18:33
 * @remark: 请添加类说明
 */
@Data
@ApiModel(value = "StsFinanceVo", description = "财务统计")
public class StsFinanceVo {

    @ApiModelProperty(value = "客户")
    private StsMeasures<Long> customer;

    @ApiModelProperty(value = "气费")
    private StsMeasures<BigDecimal> gasFei;

    @ApiModelProperty(value = "气量")
    private StsMeasures<BigDecimal> gasAmount;

    @ApiModelProperty(value = "欠费")
    private StsMeasures<BigDecimal> overdraftFei;

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/3 18:35
    * @remark 这个统计的维度数据
    */
    @Data
    public static class StsMeasures<T> implements Serializable {

        @ApiModelProperty(value = "今日")
        private T today;

        @ApiModelProperty(value = "本月")
        private T month;

        @ApiModelProperty(value = "本年")
        private T year;

        @ApiModelProperty(value = "总数")
        private T total;

        @ApiModelProperty(value = "同比数据")
        private List<StsMonthCompare<T>> compareData = new ArrayList<>();
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/28 13:34
    * @remark 每个月的数据的对比
    */
    @Data
    public static class StsMonthCompare<T> implements Serializable{

        @ApiModelProperty(value = "月份说明")
        private String monStr;

        @ApiModelProperty(value = "今年")
        private T thisYearNum;

        @ApiModelProperty(value = "去年")
        private T lastYearNum;
    }

}
