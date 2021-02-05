package com.cdqckj.gmis.iot.qc.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 物联网对接接收3.0价格数据模型
 * @author: 秦川物联网科技
 * @date: 2020/10/20  16:26
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
public class PriceModel implements Serializable {
    /**
     * 用气类型id
     */
    private Integer gasTypeID;
    /**
     * 阶梯方案号（关键参数）每个用气类型，变更方案时+1,取值范围：1~127，超过127时从1开始重新计数
     */
    private Integer priceNum;
    /**
     * 阶梯数
      */
    private Integer tieredNum;
    /**
     * 阶梯气价周期
     */
    private Integer cycle;
    /**
     * 阶梯周期切换日
     */
    private Integer adjDay;
    /**
     * 阶梯周期切换月
     */
    private Integer adjMonth;
    /**
     * 价格有限期开始日期
     */
    private Long startDate;
    /**
     * 价格有效期终止日期,如无终止日期，则取值UTC(1970-1-1) or 空. 没有启用
     */
    private Long endDate;
    /**
     * 表端调价时是否归零周期累计用气量
     * 清零（true）/不清零（false）
     */
    private Boolean isClear;
    /**
     * 阶梯起始量,默认是0
     */
    private Double initAmount;
    /**
     * 价格信息,只包含1阶信息,若需多阶梯,请调用调价接口
     */
    private List<TieredModel> tiered;
}
