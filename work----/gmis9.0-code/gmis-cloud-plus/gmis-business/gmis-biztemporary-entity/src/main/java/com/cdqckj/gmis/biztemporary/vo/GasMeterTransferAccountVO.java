package com.cdqckj.gmis.biztemporary.vo;


import com.cdqckj.gmis.biztemporary.entity.GasmeterTransferAccount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 变更记录统一返回对象
 *
 * @author hp
 */
@Data
public class GasMeterTransferAccountVO extends GasmeterTransferAccount {

    /**
     * 原客户名
     */
    @ApiModelProperty(value = "原客户名")
    private String oldCustomerName;
    /**
     * 新客户名字
     */
    @ApiModelProperty(value = "新客户名字")
    private String customerName;

    /**
     * 证件号
     */
    @ApiModelProperty(value = "证件号")
    private String idNumber;
    /**
     * 居民 商福 工业 低保
     */
    @ApiModelProperty(value = "居民 商福 工业 低保")
    private String customerTypeName;
    /**
     * 证件类型名称
     */
    @ApiModelProperty(value = "证件类型名称")
    private String idTypeName;
    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String idCard;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String sex;
    /**
     * 收费金额
     */
    @ApiModelProperty("收费金额")
    private BigDecimal totalMoney;

    @ApiModelProperty("表身号")
    private String gasMeterNumber;
}
