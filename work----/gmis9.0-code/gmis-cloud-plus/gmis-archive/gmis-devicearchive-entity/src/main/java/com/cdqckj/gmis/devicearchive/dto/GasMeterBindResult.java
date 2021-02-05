package com.cdqckj.gmis.devicearchive.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GasMeterBindResult", description = "气表返回信息App用")
public class GasMeterBindResult {
    @ApiModelProperty("客户编号")
    private String customerCode;

    @ApiModelProperty("客户姓名")
    private String customerName;

    @ApiModelProperty("电话号码")
    private String telPhone;

    /** 气表相关 **/
    @ApiModelProperty("气表编号")
    private String gasCode;

    @ApiModelProperty(value = "安装/用气地址")
    private String gasMeterAddress;

    @ApiModelProperty(value = "开户时间")
    private LocalDateTime openAccountTime;

    @ApiModelProperty(value = "调压箱号")
    private String nodeNumber;

    @ApiModelProperty(value = "表身号")
    private String gasMeterNumber;

    @ApiModelProperty("表类型code")
    private String gasMeterTypeCode;

    @ApiModelProperty(value = "表类型名称")
    private String gasMeterTypeName;


    @ApiModelProperty(value = "用气类型编号")
    private String useGasTypeCode;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    private String useGasTypeName;


    @ApiModelProperty(value = "累计用气")
    private BigDecimal cumulativeGas;


    @ApiModelProperty(value = "绑定状态")
    private  Boolean bindStatus;
}
