package com.cdqckj.gmis.readmeter.vo;

import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 实体类
 * 物联网抄表数据页面展示对象
 * </p>
 *
 * @author gmis
 * @since 2020-12-18
 */
@Data
public class ReadMeterDataIotVo extends ReadMeterDataIot {

    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo;

    /**
     * 订单来源 order_source_name 表种类
     */
    @ApiModelProperty(value = "订单来源")
    private String orderSourceName;
}
