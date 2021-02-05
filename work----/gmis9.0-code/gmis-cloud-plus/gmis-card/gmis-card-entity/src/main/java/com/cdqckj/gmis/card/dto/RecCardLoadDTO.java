package com.cdqckj.gmis.card.dto;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 实体类
 * 补卡加载数据
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
@ApiModel(value = "RecCardLoadDTO", description = "补卡加载数据")
public class RecCardLoadDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否可回收卡
     */
    @ApiModelProperty(value = "是否可回收卡")
    private Boolean isRecCard;


    /**
     * 不可回收描述
     */
    @ApiModelProperty(value = "不可回收描述")
    private String msg;

    /**
     * 卡号
     */
    @ApiModelProperty(value = "卡号")
    @Length(max = 32, message = "卡号长度不能超过32")
    private String cardNo;
    /**
     * 气表编码
     */
    @ApiModelProperty(value = "气表编码")
    @Length(max = 32, message = "气表编码长度不能超过32")
    private String gasMeterCode;
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
     * 回收卡指令数据
     */
    @ApiModelProperty(value = "回收卡指令数据")
    private JSONObject recCardJsonData;
}
