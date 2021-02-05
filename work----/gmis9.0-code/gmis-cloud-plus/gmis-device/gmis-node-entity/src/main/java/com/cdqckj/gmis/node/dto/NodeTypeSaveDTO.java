package com.cdqckj.gmis.node.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-07-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "NodeTypeSaveDTO", description = "")
public class NodeTypeSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    @Length(max = 32, message = "厂家ID长度不能超过32")
    private String nodeFacotryId;
    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    @Length(max = 50, message = "型号名称长度不能超过50")
    private String nodeName;
    /**
     * 型号编码
     */
    @ApiModelProperty(value = "型号编码")
    @Length(max = 10, message = "型号编码长度不能超过10")
    private String nodeCode;
    /**
     * 型号说明
     */
    @ApiModelProperty(value = "型号说明")
    @Length(max = 100, message = "型号说明长度不能超过100")
    private String remark;
    /**
     * RTU:rtu设备
     *             FLOWMETER:流量计
     */
    @ApiModelProperty(value = "RTU:rtu设备")
    @Length(max = 10, message = "RTU:rtu设备长度不能超过10")
    private String type;
    /**
     * TEMPERATURE:温度监测
     *             PRESSURE:压力监测
     *             FLOW:流量监测
     *             VALVE:阀门控制
     * 
     *             注：多选
     * 
     *             
     */
    @ApiModelProperty(value = "TEMPERATURE:温度监测")
    @Length(max = 500, message = "TEMPERATURE:温度监测长度不能超过500")
    private String function;
    /**
     * 启用
     *             禁用
     */
    @ApiModelProperty(value = "启用")
    private Integer nodeStatus;

}
