package com.cdqckj.gmis.node.dto;

import com.cdqckj.gmis.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
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
@ApiModel(value = "NodeFactoryUpdateDTO", description = "")
public class NodeFactoryUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

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
