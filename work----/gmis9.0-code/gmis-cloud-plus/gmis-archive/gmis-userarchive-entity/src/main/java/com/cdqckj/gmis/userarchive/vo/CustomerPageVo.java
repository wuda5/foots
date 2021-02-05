package com.cdqckj.gmis.userarchive.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data

@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)

@ApiModel(value = "CustomerPageVo", description = "客户列表参数")
public class CustomerPageVo implements Serializable {
    private Integer pageNo=1;
    private  Integer pageSize=10;

    @ApiModelProperty(value = "客户编号")
    private String customerCode;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "联系电话")
    private String telphone;

    @ApiModelProperty(value = "客户类型编号")
    private String customerTypeCode;

    @ApiModelProperty(value = "气表编号")
    private String gasCode;
    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo ;
    @ApiModelProperty(value = "安装地址")
    private String moreGasMeterAddress;

    @ApiModelProperty(value = "用气类型")
    private Long useGasTypeId;

    @ApiModelProperty(value = "删除状态")
    private Integer deleteStatus;

    @ApiModelProperty(value = "预建档 有效 拆除")
    private Integer dataStatus;


    @ApiModelProperty(value = "客户状态 预建档 0，有效1 无效2")
    private Integer customerStatus;

    @ApiModelProperty(value = "黑名单状态")
    private Integer blackStatus;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    private String gasMeterNumber;


    private List<Long> orgIds;


}
