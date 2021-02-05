package com.cdqckj.gmis.userarchive.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GasMeterCustomerParme", description = "参数")
public class GasMeterCustomerParme implements Serializable {

    @ApiModelProperty(value = "起始页")
    private Integer pageNo;

    @ApiModelProperty(value = "每页条数")
    private  Integer pageSize;
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    private String customerCode;
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;
    @ApiModelProperty(value = "客户类型ID")
    private Long customerTypeId;
    @ApiModelProperty(value = "联系电话")
    @Length(max = 11, message = "联系电话长度不能超过11")
    private String telphone;
    @ApiModelProperty(value = "用气类型ID")
    private Long useGasTypeId;
    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    private String moreGasMeterAddress;
    @ApiModelProperty(value = "气表编号")
    @Length(max = 100, message = "气表编号长度不能超过100")
    private String gasCode ;

    /**
     * 客户缴费编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    private String customerChargeNo;

    @Length(max = 100, message = "小区")
    private Long communityId ;
    @ApiModelProperty(value = "抄表册id")
    private Long bookId;
    @ApiModelProperty(value = "气表编号集合")
    private List<String> gasCodeNotInList;
    @ApiModelProperty(value = "气表类型编码")
    private String gasMeterTypeCode;

    @ApiModelProperty(value = "表身号")
    private String gasMeterNumber;
    /**
     * 所属街道d
     */
    @ApiModelProperty(value = "所属街道id")
    private Long streetId;
}
