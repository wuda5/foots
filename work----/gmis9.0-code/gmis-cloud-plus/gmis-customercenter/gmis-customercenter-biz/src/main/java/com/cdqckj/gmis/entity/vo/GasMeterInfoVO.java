package com.cdqckj.gmis.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 气表详情实体类
 * @author hc
 * @date 2020/10/16
 */
@Data
@ApiModel(value = "GasMeterInfoVO",description = "气表详情实体类")
public class GasMeterInfoVO extends CusDetailVO{

    @ApiModelProperty("绑定的燃气表的关联关系id")
    private Long binId;

    /** 气表相关 **/
    @ApiModelProperty("气表编号")
    private String gasCode;

    @ApiModelProperty(value = "安装/用气地址")
    private String gasMeterAddress;

    @ApiModelProperty(value = "开户时间")
    private Date openAccountTime;

    @ApiModelProperty(value = "调压箱号")
    private String nodeNumber;

    @ApiModelProperty(value = "表身号")
    private String gasMeterNumber;

    @ApiModelProperty("表类型code:{GENERAL_GASMETER:普表,CARD_GASMETER:卡表,INTERNET_GASMETER:物联网IOC表}")
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

    @ApiModelProperty(value = "是否为绑定气表")
    private Boolean whetherBind = false;

}
