package com.cdqckj.gmis.iot.qc.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ChangePriceBatVO implements Serializable {
    /**
     * 设备档案id
     */
    private String archiveID;
    /**
     * 当前用气类型id
     */
    private Integer curGasTypeID;
    /**
     * 新用气类型id
     */
    private Integer newGasTypeID;
    /**
     * 价格号
     */
    private Integer priceNum;
    /**
     * 周期数
     */
    private Integer cycle;
    /**
     * 周期切换日
     */
    private Integer adjDay;
    /**
     * 阶梯周期切换月，取值：1~12
     */
    private Integer adjMonth;
    /**
     *价格开始日期
     */
    private Long startdate;
    /**
     * 结束日期
     */
    private Long enddate;
    /**
     * 是否清零
     */
    private Boolean isClear;
    /**
     * 阶梯起始量
     */
    private Double initAmount;
    /**
     * 阶梯数
     */
    private Integer tieredNum;
    /**
     * 价格信息，最大支持5阶
     */
    private List<TieredModel> tiered;
}
