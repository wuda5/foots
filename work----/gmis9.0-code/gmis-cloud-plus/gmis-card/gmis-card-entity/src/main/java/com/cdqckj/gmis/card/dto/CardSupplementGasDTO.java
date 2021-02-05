package com.cdqckj.gmis.card.dto;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 实体类
 * 补气记录
 * </p>
 *
 * @author gmis
 * @since 2020-12-10
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "CardSupplementGasDTO", description = "补气记录")
@AllArgsConstructor
public class CardSupplementGasDTO extends Entity<Long> {
    /**
     * 补气读卡数据
     */
    @ApiModelProperty(value = "补气读卡数据")
    private JSONObject readCardData;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    private String gasMeterCode;

    /**
     * 补充方式：补上次充值、按需补充
     */
    @ApiModelProperty(value = "补充方式：补上次充值、按需补充")
    private String repGasMethod;


    /**
     * 补充值
     */
    @ApiModelProperty(value = "补充值")
    private BigDecimal repVal;

}
