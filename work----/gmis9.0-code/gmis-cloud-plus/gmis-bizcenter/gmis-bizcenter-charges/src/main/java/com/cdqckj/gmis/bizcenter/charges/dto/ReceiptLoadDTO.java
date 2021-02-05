package com.cdqckj.gmis.bizcenter.charges.dto;

import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 实体类
 * 开票数据加载
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
@ApiModel(value = "ReceiptLoadDTO", description = "开票数据加载")
public class ReceiptLoadDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "收费员")
    private String chargeUser;

    @ApiModelProperty(value = "缴费单号")
    private String chargeNo;

    @ApiModelProperty(value = "票据单号")
    private String invoiceNo;

    @ApiModelProperty(value = "票据公司名称")
    private String invoiceCompanyName;

    @ApiModelProperty(value = "收费时间")
    private LocalDateTime createTime;

    /**
     * 应收金额
     */
    @ApiModelProperty(value = "应收金额")
    private BigDecimal receivableMoney;
    /**
     * 实收金额
     */
    @ApiModelProperty(value = "实收金额")
    private BigDecimal actualIncomeMoney;
    /**
     * 找零
     */
    @ApiModelProperty(value = "找零")
    private BigDecimal giveChange;


    /**
     * 合计
     */
    @ApiModelProperty(value = "合计")
    private BigDecimal totalMoney;


    /**
     * 合计大写
     */
    @ApiModelProperty(value = "合计大写")
    private String totalMoneyChinese;


//    @ApiModelProperty(value = "账户抵扣")
//    private BigDecimal prechargeReductionMoney;

//    @ApiModelProperty(value = "场景收费记录IDS")
//    private List<Long> sceneIds;
//
//    @ApiModelProperty(value="抄表收费数据IDS")
//    private List<Long> arrearIds;

    @ApiModelProperty(value="收费项明细")
    private List<ChargeItemRecord> chargeItemRecords;


//    @ApiModelProperty(value = "缴费信息")
//    private ChargeRecord chargeRecord;

//    @ApiModelProperty(value="收费项系统信息（开票配置等-待用）")
//    private List<TollItem> sysTollItems;

}
