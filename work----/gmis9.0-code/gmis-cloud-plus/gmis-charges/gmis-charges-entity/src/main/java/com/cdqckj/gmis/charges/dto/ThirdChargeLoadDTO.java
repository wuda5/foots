package com.cdqckj.gmis.charges.dto;

import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import com.cdqckj.gmis.charges.entity.CustomerEnjoyActivityRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 实体类
 * 缴费加载数据封装
 * </p>
 *
 * @author tp
 * @since 2020-08-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ChargeLoadDTO", description = "缴费加载数据封装")
public class ThirdChargeLoadDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收费项(列表显示)")
    private List<ChargeItemRecord>  itemList;

    @ApiModelProperty(value = "用户编号")
    private String consNo;

    @ApiModelProperty(value = "用户名称")
    private String consName;

    @ApiModelProperty(value = "用户地址")
    private String addr;

    @ApiModelProperty(value = "组织-16位")
    private String orgNo;

    @ApiModelProperty(value = "合计金额")
    private BigDecimal totalMoney;



}
