package com.cdqckj.gmis.bizcenter.charges.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 实体类
 * 缴费项记录
 * </p>
 *
 * @author tp
 * @since 2020-10-13
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "LimitLoadDTO", description = "限额配置加载")
@AllArgsConstructor
public class LimitLoadDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 个人单笔限额
     */
    @ApiModelProperty(value = "个人单笔限额，0不限额")
    private BigDecimal userSingleLimit;

    /**
     * 个人可操作余额
     */
    @ApiModelProperty(value = "个人可操作余额，-1不限额")
    private BigDecimal userBalance;

    /**
     * 营业厅可操作余额
     */
    @ApiModelProperty(value = "营业厅可操作余额，-1不限额")
    private BigDecimal bizHallBalance;


    /**
     * 营业厅单笔限额
     */
    @ApiModelProperty(value = "营业厅单笔限额，0不限额")
    private BigDecimal bizHallSingleLimit;

}
