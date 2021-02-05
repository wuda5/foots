package com.cdqckj.gmis.card.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 实体类
 * 气表卡信息
 * </p>
 *
 * @author tp
 * @since 2020-11-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CardInfoSaveDTO", description = "气表卡信息")
public class CardRecReadDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "卡信息获取失败原因")
    private String cardStatus;
    /**
     * 卡上合计量
     */
    @ApiModelProperty(value = "卡上合计量")
    private BigDecimal totalAmount;
    /**
     * 卡上余额
     */
    @ApiModelProperty(value = "卡上余额")
    private BigDecimal cardBalance;
    /**
     * 卡上充值次数
     */
    @ApiModelProperty(value = "卡上充值次数")
    private Integer cardChargeCount;
    /**
     * 数据状态:
     *             1: 正常
     *             0: 作废
     */
    @ApiModelProperty(value = "数据状态:")
    private Integer dataStatus;

}
