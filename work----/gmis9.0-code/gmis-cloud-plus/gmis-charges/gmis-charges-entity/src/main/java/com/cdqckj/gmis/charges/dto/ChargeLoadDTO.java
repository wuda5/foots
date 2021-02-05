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
public class ChargeLoadDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收费项(列表显示)")
    private List<ChargeItemRecord>  itemList;

    @ApiModelProperty(value = "收费项(待加收费项)")
    private List<ChargeItemRecord>  waiteAppendItemList;

    @ApiModelProperty(value = "充值赠送活动")
    private List<CustomerEnjoyActivityRecord> rechargeActivityList;

    @ApiModelProperty(value = "账户金额")
    private BigDecimal accountMoney;

    @ApiModelProperty(value = "户表余额")
    private BigDecimal accountMeterMoney;

    @ApiModelProperty(value = "合计金额")
    private BigDecimal totalMoney;

    @ApiModelProperty(value = "保险到期日期")
    private String insuranceExpireDate;

    @ApiModelProperty(value = "是否购买保险 true购买 false未购买")
    private Boolean isBuyInsurance;

    @ApiModelProperty(value = "保险是否已过期 true已过期 false未过期")
    private Boolean insuranceIsExpire;

    @ApiModelProperty(value = "是否输入票据编号 true人工输入 false自动生成")
    private Boolean manualInput;

    @ApiModelProperty(value = "是否开启购买保险 true 开启 false不购买保险")
    private Boolean isOpenInsurance;


    @ApiModelProperty(value = "是否开户收费,是true 否false")
    private Boolean isOpenAccount=false;


    @ApiModelProperty(value = "存在未写卡记录 true 存在，false 不存在")
    private Boolean haveNoWriteCardRecord=false;

}
