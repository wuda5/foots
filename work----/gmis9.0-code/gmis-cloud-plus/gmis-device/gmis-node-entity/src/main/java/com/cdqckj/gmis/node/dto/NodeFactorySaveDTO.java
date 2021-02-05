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
@ApiModel(value = "NodeFactorySaveDTO", description = "")
public class NodeFactorySaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    private String nodeFacotryName;
    /**
     * 厂家编号
     */
    @ApiModelProperty(value = "厂家编号")
    @Length(max = 10, message = "厂家编号长度不能超过10")
    private String nodeFacotryCode;
    /**
     * RTU:rtu设备
     *             FLOWMETER:流量计
     */
    @ApiModelProperty(value = "RTU:rtu设备")
    @Length(max = 100, message = "RTU:rtu设备长度不能超过100")
    private String facotryUseNodeType;
    /**
     * 启用
     * 	禁用
     */
    @ApiModelProperty(value = "启用")
    private Integer nodeFactoryStatus;

}
