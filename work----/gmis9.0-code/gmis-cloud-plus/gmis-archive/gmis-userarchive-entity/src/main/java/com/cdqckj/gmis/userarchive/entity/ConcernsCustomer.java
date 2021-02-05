package com.cdqckj.gmis.userarchive.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcernsCustomer {
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String telphone;
    /**
     * 客户类型ID
     */
    @ApiModelProperty(value = "客户类型Code")
    private String customerTypeCode;

    /**
     * 居民 商福 工业 低保
     */
    @ApiModelProperty(value = "居民 商福 工业 低保")
    private String customerTypeName;
    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    private Long useGasTypeId;
    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    private String useGasTypeName;
    /**
     * 客户地址
     */
    @ApiModelProperty(value = "客户地址")
    private String contactAddress;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
