package com.cdqckj.gmis.userarchive.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CustomerSecurityQuerypParametersVo", description = "客户安检查询参数")
public class CustomerSecurityQuerypParametersVo implements Serializable {
    @ApiModelProperty(value = "起始页")
    private Integer pageNo;

    @ApiModelProperty(value = "每页条数")
    private  Integer pageSize;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "街道ID")
    private Long streetId ;

    @ApiModelProperty(value = "小区ID")
    private Long communityId ;

    @ApiModelProperty(value = "安检员")
    private Long securityCheckUserId;

    @ApiModelProperty(value = "用气类型ID")
    private Long    useGasTypeId ;

    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    private String customerCode;

    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;

    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    private String contactAddress ;

    @ApiModelProperty(value = "调压箱编号")
    private Long nodeNumber ;

}
