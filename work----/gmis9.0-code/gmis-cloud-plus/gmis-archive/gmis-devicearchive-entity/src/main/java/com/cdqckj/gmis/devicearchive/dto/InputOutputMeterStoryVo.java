package com.cdqckj.gmis.devicearchive.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "InputOutputMeterStoryVo", description = "")
public class InputOutputMeterStoryVo {

    /**
     * 批次号
     */
    @ApiModelProperty(value = "批次号")
    @Length(max = 100, message = "批次号长度不能超过100")
    private String serialCode;
    @ApiModelProperty(value = "")
    @Length(max = 32, message = "长度不能超过32")
    private String gasMeterFactoryId;
    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    private String gasMeterFactoryName;
    /**
     * 版号id
     */
    @ApiModelProperty(value = "版号id")
    @Length(max = 32, message = "版号id长度不能超过32")
    private String gasMeterVersionId;
    /**
     * 版号名称
     */
    @ApiModelProperty(value = "版号名称")
    @Length(max = 30, message = "版号名称长度不能超过30")
    private String gasMeterVersionName;
    /**
     * 普表
     *             卡表
     *             物联网表
     *             流量计(普表)
     *             流量计(卡表）
     *             流量计(物联网表）
     */
    @ApiModelProperty(value = "普表")
    @Length(max = 32, message = "普表长度不能超过32")
    private String gasMeterTypeId;
    @ApiModelProperty(value = "")
    @Length(max = 30, message = "长度不能超过30")
    private String gasMeterTypeName;
    /**
     * 1表示出库
     * 			0表示入库
     *
     */
    @ApiModelProperty(value = "1表示出库")
    private Integer status;
    /**
     * 表示库存数量
     */
    @ApiModelProperty(value = "表示库存数量")
    private Long storeCount;


}
